package cz.osu.prf.kip.exceptions;

import cz.osu.prf.kip.Book;

public class DuplicitBookException extends BookBaseException {
  public DuplicitBookException(Book book) {
    super(book);
  }
}
