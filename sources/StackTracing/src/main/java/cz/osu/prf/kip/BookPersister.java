package cz.osu.prf.kip;

import cz.osu.prf.kip.exceptions.BookDataException;
import cz.osu.prf.kip.exceptions.BookPersisterException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class BookPersister {
    private final static String DELIMITER = "-#O#-";
    private final Path path;
    private Object currentLockingObject;
    private final ReentrantLock lock = new ReentrantLock();
    private final AtomicBoolean lockFlag = new AtomicBoolean(false);

    public BookPersister(String s) {
        this.path = Paths.get(s);
        if (!java.nio.file.Files.exists(this.path) || !java.nio.file.Files.isDirectory(this.path))
            throw new BookPersisterException(path + " is not a directory.");
    }

    public void delete(Book book) {
        try {
            String line = encodeBookToLine(book);
            Path filePath = convertYearToFile(book.getYear());
            List<String> lines = readAllLines(filePath);
            lines.removeIf(q -> q.equals(line));
            safelyReplaceFileContent(filePath, lines);
        } catch (Exception ex) {
            throw new BookPersisterException("Failed to delete book " + book, ex);
        }
    }

    private static void safelyReplaceFileContent(Path filePath, List<String> lines) throws IOException {
        Path tmpFilePath = Paths.get(filePath.toString() + ".tmp");
        try {
            java.nio.file.Files.write(tmpFilePath, lines);
            java.nio.file.Files.delete(filePath);
            java.nio.file.Files.move(tmpFilePath, filePath);
        } catch (IOException ex) {
            String s = String.format("Failed to write result db file %s using temp file %s. Some operation has failed.",
                    filePath, tmpFilePath);
            throw new IOException(s, ex);
        }
    }

    public List<Book> getAll() {
        List<Book> ret = new ArrayList<>();
        List<Path> files = getAllDataFiles();
        for (Path file : files) {
            List<String> lines = readAllLines(file);
            List<Book> tmp = decodeLines(lines);
            ret.addAll(tmp);
        }
        return ret;
    }

    public void persist(Book book) {
        validateBook(book);
        Path file = convertYearToFile(book.getYear());
        persist(book, file);
    }

    private void persist(Book book, Path file) {
        try {
            String line = encodeBookToLine(book);
            java.nio.file.Files.writeString(path, line + "\n", StandardOpenOption.APPEND);
        } catch (Exception ex) {
            throw new BookPersisterException("Failed to store book " + book + " to file " + file, ex);
        }
    }

    private String encodeBookToLine(Book book) {
        return book.getTitle() + DELIMITER + book.getAuthor() + DELIMITER + book.getYear();
    }

    private void validateBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty())
            throw new BookDataException("Invalid author", book);
        if (book.getTitle() == null || book.getTitle().trim().isEmpty())
            throw new BookDataException("Invalid title", book);
        if (book.getYear() < 1900 && book.getYear() > 2100)
            throw new BookDataException("Invalid year", book);
    }

    private List<Path> getAllDataFiles() {
        List<Path> ret = new ArrayList<>();
        try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(this.path, "*.bdb")) {
            for (Path entry : stream) {
                ret.add(entry);
            }
        } catch (IOException ex) {
            throw new BookPersisterException("Unable to search " + this.path + " for book database files.", ex);
        }
        return ret;
    }

    public List<Book> getByYear(int year) {
        Path path = convertYearToFile(year);
        List<String> lines = readAllLines(path);
        List<Book> ret = decodeLines(lines);
        return ret;
    }

    private List<Book> decodeLines(List<String> lines) {
        List<Book> ret = new ArrayList<>();
        for (String line : lines) {
            Book book = decodeBookFromLine(line);
            validateBook(book);
            ret.add(book);
        }
        return ret;
    }

    private Book decodeBookFromLine(String line) {
        Book ret;
        try {
            String[] pts = line.split(DELIMITER);
            if (pts.length != 3) throw new Exception("Expected 3 sections, found " + pts.length);
            int year = Integer.parseInt(pts[0]);
            ret = new Book(pts[0], pts[1], year);
        } catch (Exception ex) {
            throw new BookPersisterException("Invalid book format at line: " + line, ex);
        }
        return ret;
    }

    private List<String> readAllLines(Path path) {
        List<String> ret;

        if (!java.nio.file.Files.exists(path))
            ret = new ArrayList<>();
        else
            try {
                ret = java.nio.file.Files.readAllLines(path);
            } catch (IOException ex) {
                throw new BookPersisterException("Failed to read content of file " + path, ex);
            }
        return ret;
    }

    private Path convertYearToFile(int year) {
        Path ret = this.path.resolve(Integer.toString(year) + ".bdb");
        return ret;
    }

    public synchronized void lock(Object lockingObject) {
        if (lockingObject == null) throw new IllegalArgumentException("Locking object cannot be null.");
        if (currentLockingObject != null) throw new BookPersisterException("Already locked by " + currentLockingObject);
        currentLockingObject = lockingObject;
    }

    public void unlock(Object lockingObject) {
        if (lockingObject == null)
            throw new IllegalArgumentException("Locking object cannot be null.");
        if (currentLockingObject == null)
            throw new BookPersisterException("Unable to unlock as currently is unlocked.");
        if (!currentLockingObject.equals(lockingObject))
            throw new BookPersisterException("Unable to unlock as currently locked by " + currentLockingObject + ", but unlocking by " + lockingObject);
        currentLockingObject = null;
    }
}
