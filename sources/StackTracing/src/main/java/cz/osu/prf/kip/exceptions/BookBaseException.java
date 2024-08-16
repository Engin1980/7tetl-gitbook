package cz.osu.prf.kip.exceptions;

import cz.osu.prf.kip.Book;

public abstract class BookBaseException extends RuntimeException {
  private final Book book;
  public BookBaseException(Book book) {
    this.book = book;
  }
  public Book getBook() {
    return book;
  }
}
