package implementation;

import dto.Book;
import dto.Borrower;
import dto.Borrowing;
import helper.DatabaseConnection;
import interfaces.IBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookImpl implements IBook {
    private  final String ADD_BOOK = "INSERT INTO books (isbn, title, author, status) VALUES (?, ?, ?, ?)";
    private  final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, status = ? WHERE isbn = ?";
    private  final String DELETE_BOOK = "DELETE FROM books WHERE isbn = ?";
    private  final String SEARCH_BY_TITLE = "SELECT * FROM books WHERE title LIKE ?";
    private  final String SEARCH_BY_AUTHOR = "SELECT * FROM books WHERE author LIKE ?";
    private  final String SELECT_BOOK = "SELECT * FROM books";
    private  final String STATISTIC_BOOK = "SELECT status, COUNT(*) as count FROM books GROUP BY status";


    @Override
    public void add(Book book) {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK)) {
            preparedStatement.setInt(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, "Available");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Book book) {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getStatus().toString());
            preparedStatement.setInt(4, book.getIsbn());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int isbn) {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK)) {
            preparedStatement.setInt(1, isbn);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_TITLE)) {
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int isbn = resultSet.getInt("isbn");
                String bookTitle = resultSet.getString("title");
                String author = resultSet.getString("author");
                String statusString = resultSet.getString("status");
                Book.StatusBook status = Book.StatusBook.valueOf(statusString);

                Book book = new Book(isbn, bookTitle, author, status);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }


    @Override
    public List<Book> searchByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_AUTHOR)) {
            preparedStatement.setString(1, "%" + author + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int isbn = resultSet.getInt("isbn");
                String title = resultSet.getString("title");
                String bookAuthor = resultSet.getString("author");
                String statusString = resultSet.getString("status");
                Book.StatusBook status = Book.StatusBook.valueOf(statusString);

                Book book = new Book(isbn, title, bookAuthor, status);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }


    public List<Book> show() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int isbn = resultSet.getInt("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String statusString = resultSet.getString("status");
                Book.StatusBook status = Book.StatusBook.valueOf(statusString);

                Book book = new Book(isbn, title, author, status);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }




    public  void showStatistics() {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(STATISTIC_BOOK)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int totalBooks = 0;
            while (resultSet.next()) {
                String status = resultSet.getString("status");
                int count = resultSet.getInt("count");
                System.out.println("Status: " + status + " " + count);
                totalBooks += count;
            }
            System.out.println("Total Books: " + totalBooks);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
