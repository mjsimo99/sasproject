package dto;

import java.util.List;

public class Book {
    private int isbn;
    private String title;
    private String author;
    private StatusBook status;

    public enum StatusBook {
        Lost,
        Borrow,
        Return,

        Available
    }

    public List<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

    private List<Borrower> borrowers;



    public Book(int isbn, String title, String author, StatusBook status) {
        setIsbn(isbn);
        setTitle(title);
        setAuthor(author);
        setStatus(status);
    }

    public int getIsbn() {
        return isbn;
    }

    public StatusBook getStatus() {
        return status;
    }

    public void setStatus(StatusBook status) {
        this.status = status;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }






    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + isbn +
                ", Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", Status='" + status + '\'' +
                '}';
    }

}
