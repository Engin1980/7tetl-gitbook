package cz.osu.prf.kip;

import cz.osu.prf.kip.exceptions.DuplicitBookException;
import cz.osu.prf.kip.exceptions.NotFoundBookException;

import java.util.List;

public class BookManager {
  private final BookPersister bookPersister = new BookPersister("R:\\");

  public void addBook(Book book) {
    bookPersister.lock(this);
    List<Book> books = bookPersister.getByYear(book.getYear());
    if (books.stream().anyMatch(q -> q.equals(book)))
      throw new DuplicitBookException(book);
    bookPersister.persist(book);
    bookPersister.unlock(this);
  }

  public List<Book> getAll() {
    bookPersister.lock(this);
    List<Book> ret = bookPersister.getAll();
    bookPersister.unlock(this);
    return ret;
  }

  public void delete(Book book) {
    bookPersister.lock(this);
    List<Book> books = bookPersister.getByYear(book.getYear());
    if (books.stream().noneMatch(q -> q.equals(book)))
      throw new NotFoundBookException(book);
    bookPersister.delete(book);
    bookPersister.unlock(this);
  }
}
