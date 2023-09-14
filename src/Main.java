import dto.Book;
import dto.Borrower;
import dto.Borrowing;
import implementation.BookImpl;
import implementation.BorrowerImpl;
import implementation.BorrowingImpl;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Library Management System!");
        BookImpl bookImpl = new BookImpl();


        while (true) {
            printMainMenuOptions();

            int choice = getValidChoice();

            switch (choice) {
                case 1 -> manageBooks();
                case 2 -> manageBorrowers();
                case 3 -> manageBorrowings();
                case 4 -> exitProgram();
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printMainMenuOptions() {
        System.out.println("\nChoose an operation:");
        System.out.println("1. Manage Books");
        System.out.println("2. Manage Borrowers");
        System.out.println("3. Manage Borrowings");
        System.out.println("4. Exit");
    }

    private static int getValidChoice() {
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
                scanner.nextLine();
            }
        }
    }



    private static void exitProgram() {
        System.out.println("Exiting program.");
        System.exit(0);
    }

    private static void manageBooks() {
        BookImpl bookImpl = new BookImpl();

        while (true) {
            printBookMenuOptions();

            int choice = getValidChoice();

            switch (choice) {
                case 1 -> addBook(bookImpl);
                case 2 -> updateBook(bookImpl);
                case 3 -> deleteBook(bookImpl);
                case 4 -> searchBooksByTitle(bookImpl);
                case 5 -> searchBooksByAuthor(bookImpl);
                case 6 -> showAllBooks(bookImpl);
                case 7 -> bookImpl.showStatistics();
                case 8 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printBookMenuOptions() {
        System.out.println("\nChoose an operation for Books:");
        System.out.println("1. Add a book");
        System.out.println("2. Update a book");
        System.out.println("3. Delete a book");
        System.out.println("4. Search by title");
        System.out.println("5. Search by author");
        System.out.println("6. Show all books");
        System.out.println("7. Show statistics");
        System.out.println("8. Back to main menu");
    }

    private static void addBook(BookImpl bookImpl) {
        System.out.print("ISBN: ");
        int isbn = inputInt();

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        Book bookToAdd = new Book(isbn, title, author, null);
        bookImpl.add(bookToAdd);
        System.out.println("\nBook added successfully.");
    }

    private static void updateBook(BookImpl bookImpl) {
        System.out.print("Enter ISBN of the book to update: ");
        int isbnToUpdate = inputInt();

        Book existingBook = bookImpl.getBookByISBN(isbnToUpdate);

        if (existingBook != null) {
            System.out.print("New Title (press Enter to keep the existing title): ");
            String newTitle = scanner.nextLine();

            System.out.print("New Author (press Enter to keep the existing author): ");
            String newAuthor = scanner.nextLine();

            if (newTitle.isEmpty()) {
                newTitle = existingBook.getTitle();
            }
            if (newAuthor.isEmpty()) {
                newAuthor = existingBook.getAuthor();
            }

            Book bookToUpdate = new Book(isbnToUpdate, newTitle, newAuthor, existingBook.getStatus());

            boolean updated = bookImpl.update(bookToUpdate);

            if (updated) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Update failed.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void deleteBook(BookImpl bookImpl) {
        System.out.print("Enter ISBN of the book to delete: ");
        int isbnToDelete = inputInt();

        boolean deleted = bookImpl.delete(isbnToDelete);
        if (deleted) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found or delete failed.");
        }
    }

    private static void searchBooksByTitle(BookImpl bookImpl) {
        System.out.print("Enter the title to search for: ");
        String searchTitle = scanner.nextLine();
        List<Book> booksByTitle = bookImpl.searchByTitle(searchTitle);
        System.out.println("Books found by title:");
        for (Book book : booksByTitle) {
            System.out.println(book);
        }
    }

    private static void searchBooksByAuthor(BookImpl bookImpl) {
        System.out.print("Enter the author to search for: ");
        String searchAuthor = scanner.nextLine();
        List<Book> booksByAuthor = bookImpl.searchByAuthor(searchAuthor);
        System.out.println("Books found by author:");
        for (Book book : booksByAuthor) {
            System.out.println(book);
        }
    }

    private static void showAllBooks(BookImpl bookImpl) {
        List<Book> allBooks = bookImpl.show();
        System.out.println("All Books:");
        for (Book book : allBooks) {
            System.out.println(book);
        }
    }


    private static int inputInt() {
        while (true) {
            System.out.print("type int: ");
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Input must be a numeric value. Please try again.");
            }
        }
    }






    private static void manageBorrowers() {
        BorrowerImpl borrowerImpl = new BorrowerImpl();

        while (true) {
            printBorrowerMenuOptions();

            int choice = getValidChoice();

            switch (choice) {
                case 1 -> addBorrower(borrowerImpl);
                case 2 -> updateBorrower(borrowerImpl);
                case 3 -> deleteBorrower(borrowerImpl);
                case 4 -> showAllBorrowers(borrowerImpl);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printBorrowerMenuOptions() {
        System.out.println("\nChoose an operation for Borrowers:");
        System.out.println("1. Add a borrower");
        System.out.println("2. Update a borrower");
        System.out.println("3. Delete a borrower");
        System.out.println("4. Show all borrowers");
        System.out.println("5. Back to main menu");
    }


    private static void addBorrower(BorrowerImpl borrowerImpl) {
        System.out.print("Borrower Name: ");
        String borrowerName = scanner.nextLine();

        System.out.print("Telephone Number: ");
        int telephoneNumber = inputInt();


        System.out.print("CIN: ");
        String cin = scanner.nextLine();


        Borrower borrowerToAdd = new Borrower(0, borrowerName, telephoneNumber, cin);
        borrowerImpl.add(borrowerToAdd);
        System.out.println("Borrower added successfully.");
    }

    private static void updateBorrower(BorrowerImpl borrowerImpl) {
        System.out.print("Enter ID of the borrower to update: ");
        int borrowerIdToUpdate = inputInt();

        System.out.print("New Borrower Name: ");
        String newBorrowerName = scanner.nextLine();

        System.out.print("New Telephone Number: ");
        int newTelephoneNumber = inputInt();

        System.out.print("New CIN: ");
        String newCin = scanner.nextLine();


        Borrower borrowerToUpdate = new Borrower(borrowerIdToUpdate, newBorrowerName, newTelephoneNumber, newCin);

        boolean updated = borrowerImpl.update(borrowerToUpdate);

        if (updated) {
            System.out.println("Borrower updated successfully.");
        } else {
            System.out.println("Borrower not found or update failed.");
        }
    }


    private static void deleteBorrower(BorrowerImpl borrowerImpl) {
        System.out.print("Enter Cin of the borrower to delete: ");
        String borrowerIdToDelete = scanner.nextLine();
        boolean deleted = borrowerImpl.delete(String.valueOf(borrowerIdToDelete));
        if (deleted) {
            System.out.println("Borrower deleted successfully.");
        } else {
            System.out.println("Borrower not found or delete failed.");
        }
    }

    private static void showAllBorrowers(BorrowerImpl borrowerImpl) {
        List<Borrower> allBorrowers = borrowerImpl.show();
        System.out.println("All Borrowers:");
        for (Borrower borrower : allBorrowers) {
            System.out.println(borrower);
        }
    }

    private static void manageBorrowings() {
        BorrowingImpl borrowingImpl = new BorrowingImpl();
        BookImpl bookImpl = new BookImpl();
        BorrowerImpl borrowerImpl = new BorrowerImpl();


        while (true) {
            printBorrowingMenuOptions();

            int choice = getValidChoice();

            switch (choice) {
                case 1 -> borrowBook(borrowingImpl, bookImpl, borrowerImpl);
                case 2 -> returnBook(borrowingImpl,bookImpl);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printBorrowingMenuOptions() {
        System.out.println("\nChoose an operation for Borrowings:");
        System.out.println("1. Borrow a book");
        System.out.println("2. Return a book");
        System.out.println("3. Back to main menu");
    }

    private static void borrowBook(BorrowingImpl borrowingImpl, BookImpl bookImpl, BorrowerImpl borrowerImpl) {
        System.out.print("Enter ISBN of the book to borrow: ");
        int isbnToBorrow = inputInt();


        System.out.print("Enter the borrower's ID: ");

        int borrowerId = inputInt();


        Book bookToBorrow = bookImpl.getBookByISBN(isbnToBorrow);
        Borrower borrower = borrowerImpl.getBorrowerById(borrowerId);

        if (bookToBorrow == null) {
            System.out.println("Book not found.");
        } else if (borrower == null) {
            System.out.println("Borrower not found.");
        } else if (bookToBorrow.getStatus() == Book.StatusBook.Available) {
            borrowingImpl.borrow(bookToBorrow, borrower);
            System.out.println("Book borrowed successfully.");
        } else if (bookToBorrow.getStatus() == Book.StatusBook.Borrow) {
            System.out.println("This book is already borrowed.");
        } else {
            System.out.println("This book is not available for borrowing.");
        }
    }




    private static void returnBook(BorrowingImpl borrowingImpl, BookImpl bookImpl) {
        System.out.print("Enter ISBN of the book to return: ");

        int isbnToReturn = inputInt();

        System.out.print("Enter the borrower's ID: ");

        int borrowerIdToReturn = inputInt();

        Book bookToReturn = bookImpl.getBookByISBN(isbnToReturn);

        if (bookToReturn != null && bookToReturn.getStatus() == Book.StatusBook.Borrow) {
            Borrower borrowerToReturn = new Borrower(borrowerIdToReturn, "", 0, "");
            Borrowing updatedBorrowing = borrowingImpl.returnBook(bookToReturn, borrowerToReturn);

            if (updatedBorrowing != null) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Return book failed.");
            }
        } else if (bookToReturn != null && bookToReturn.getStatus() == Book.StatusBook.Available) {
            System.out.println("Book is already available.");
        } else {
            System.out.println("Book not found or not currently borrowed.");
        }
    }


}
