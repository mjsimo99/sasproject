package dto;

import java.sql.Date;

public class Borrowing {

 private Book book;

 private Borrower borrower ;

 private Date borrowingDate;

 private Date returnDate;

 private int borrowingDuration;




    public Borrowing(Book book, Borrower borrower, Date borrowingDate, Date returnDate, int borrowingDuration) {
        setBook(book);
        setBorrower(borrower);
        setBorrowingDate(borrowingDate);
        setReturnDate(returnDate);
        setBorrowingDuration(borrowingDuration);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getBorrowingDuration() {
        return borrowingDuration;
    }

    public void setBorrowingDuration(int borrowingDuration) {
        this.borrowingDuration = borrowingDuration;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "book=" + book +
                ", borrower=" + borrower +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                ", borrowingDuration=" + borrowingDuration +
                '}';
    }
}
