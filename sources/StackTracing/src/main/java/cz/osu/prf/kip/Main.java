package cz.osu.prf.kip;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    BookManager manager = new BookManager();

    Book book = new Book("The Mist", "Stephen King", 1980 );
    manager.addBook(book);
  }
}
