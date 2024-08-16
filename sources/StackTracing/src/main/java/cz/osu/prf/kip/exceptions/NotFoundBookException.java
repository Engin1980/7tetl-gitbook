package cz.osu.prf.kip.exceptions;

import cz.osu.prf.kip.Book;

public class NotFoundBookException extends BookBaseException{
  public NotFoundBookException(Book book) {
    super(book);
  }
}
