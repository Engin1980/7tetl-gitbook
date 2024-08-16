package cz.osu.prf.kip.exceptions;

import cz.osu.prf.kip.Book;

public class BookDataException extends BookBaseException {
  private final String reason;

  public BookDataException(String reason, Book book) {
    super(book);
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
