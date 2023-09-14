package implementation;

import dto.Book;
import helper.DatabaseConnection;
import interfaces.IBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookImpl implements IBook {
    private static final String ADD_BOOK = "INSERT INTO books (isbn, title, author, status) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, status = ? WHERE isbn = ?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE isbn = ?";
    private static final String SEARCH_BY_TITLE = "SELECT * FROM books WHERE title LIKE ?";
    private static final String SEARCH_BY_AUTHOR = "SELECT * FROM books WHERE author LIKE ?";
    private static final String SELECT_BOOK = "SELECT * FROM books";
    private static final String STATISTIC_BOOK = "SELECT status, COUNT(*) as count FROM books GROUP BY status";

    public BookImpl() {
        this.updateBooksStatus();
    }



    @Override
    public void add(Book book) {
        Connection connection = DatabaseConnection.getConn();
        try (
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
        Connection connection = DatabaseConnection.getConn();
        try (
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
        Connection connection = DatabaseConnection.getConn();
        try (
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
        Connection connection = DatabaseConnection.getConn();
        try (
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
        Connection connection = DatabaseConnection.getConn();
        try (
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

    @Override
    public List<Book> show() {
        List<Book> books = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (
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



    @Override
    public void showStatistics() {
        Connection connection = DatabaseConnection.getConn();

        try (
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
    @Override

    public Book getBookByISBN(int isbn) {
        Connection connection = DatabaseConnection.getConn();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE isbn = ?")) {
            preparedStatement.setInt(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String statusString = resultSet.getString("status");
                Book.StatusBook status = Book.StatusBook.valueOf(statusString);

                return new Book(isbn, title, author, status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void updateBooksStatus() {
        Connection connection = DatabaseConnection.getConn();

        try {
            Date currentTime = new Date(System.currentTimeMillis());

            long borrowingDurationMillis = 10 * 24 * 60 * 60 * 1000L;
            Date dueDate = new Date(currentTime.getTime() - borrowingDurationMillis);

            String updateQuery = "UPDATE books " +
                    "SET status = 'Lost' "  +
                    "WHERE status = 'Borrow' " +
                    "AND EXISTS (SELECT 1 FROM borrowings br " +
                    "            WHERE br.book_isbn = books.isbn " +
                    "            AND ? > (br.borrowing_date + ?::interval))";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setTimestamp(1, new Timestamp(currentTime.getTime()));
                preparedStatement.setString(2, borrowingDurationMillis + " milliseconds");

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
