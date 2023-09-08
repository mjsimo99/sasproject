package interfaces;

import dto.Book;
import dto.Borrower;
import dto.Borrowing;

public interface IBorrowing {

    Borrowing borrow(Book book, Borrower borrower);

    Borrowing returnBook(Book book, Borrower borrower);


}
