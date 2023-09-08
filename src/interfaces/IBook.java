package interfaces;

import dto.Book;

import java.util.List;

public interface IBook {
    void add(Book book);
    boolean update(Book book);
    boolean delete(int isbn);

    List<Book> searchByTitle(String title);

    List<Book> searchByAuthor(String title);

    List<Book> show();




}
