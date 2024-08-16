package cz.osu.prf.kip;

public class Book {
  private final String title;
  private final String author;
  private final int year;

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public int getYear() {
    return year;
  }

  public Book(String title, String author, int year) {
    this.title = title;
    this.author = author;
    this.year = year;
  }
}
