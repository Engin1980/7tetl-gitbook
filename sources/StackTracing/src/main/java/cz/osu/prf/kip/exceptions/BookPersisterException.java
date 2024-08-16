package cz.osu.prf.kip.exceptions;

public class BookPersisterException extends RuntimeException {

  public BookPersisterException(String message) {
    super(message);
  }

  public BookPersisterException(String message, Throwable cause) {
    super(message, cause);
  }
}
