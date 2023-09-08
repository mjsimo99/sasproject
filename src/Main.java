import dto.Book;
import dto.Borrower;
import dto.Borrowing;
import implementation.BookImpl;
import implementation.BorrowerImpl;
import implementation.BorrowingImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library Management System!");

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Borrowers");
            System.out.println("3. Manage Borrowings");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manageBooks(scanner);
                case 2 -> manageBorrowers(scanner);
                case 3 -> manageBorrowings(scanner);
                case 4 -> {
                    System.out.println("Exiting program.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void manageBooks(Scanner scanner) {
        BookImpl bookImpl = new BookImpl();

        while (true) {
            System.out.println("\nChoose an operation for Books:");
            System.out.println("1. Add a book");
            System.out.println("2. Update a book");
            System.out.println("3. Delete a book");
            System.out.println("4. Search by title");
            System.out.println("5. Search by author");
            System.out.println("6. Show all books");
            System.out.println("7. Show statistics");
            System.out.println("8. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("ISBN: ");
                    int isbn = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    Book bookToAdd = new Book(isbn, title, author, null);
                    bookImpl.add(bookToAdd);
                    System.out.println("\nBook added successfully.");

                }
                case 2 -> {
                    System.out.print("Enter ISBN of the book to update: ");
                    int isbnToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("New Title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("New Author: ");
                    String newAuthor = scanner.nextLine();

                    Book bookToUpdate = new Book(isbnToUpdate, newTitle, newAuthor, Book.StatusBook.Available);

                    boolean updated = bookImpl.update(bookToUpdate);

                    if (updated) {
                        System.out.println("Book updated successfully.");
                    } else {
                        System.out.println("Book not found or update failed.");
                    }
                }

                case 3 -> {
                    System.out.print("Enter ISBN of the book to delete: ");
                    int isbnToDelete = scanner.nextInt();
                    scanner.nextLine();
                    boolean deleted = bookImpl.delete(isbnToDelete);
                    if (deleted) {
                        System.out.println("Book deleted successfully.");
                    } else {
                        System.out.println("Book not found or delete failed.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter the title to search for: ");
                    String searchTitle = scanner.nextLine();
                    List<Book> booksByTitle = bookImpl.searchByTitle(searchTitle);
                    System.out.println("Books found by title:");
                    for (Book book : booksByTitle) {
                        System.out.println(book);
                    }
                }
                case 5 -> {
                    System.out.print("Enter the author to search for: ");
                    String searchAuthor = scanner.nextLine();
                    List<Book> booksByAuthor = bookImpl.searchByAuthor(searchAuthor);
                    System.out.println("Books found by author:");
                    for (Book book : booksByAuthor) {
                        System.out.println(book);
                    }
                }
                case 6 -> {
                    List<Book> allBooks = bookImpl.show();
                    System.out.println("All Books:");
                    for (Book book : allBooks) {
                        System.out.println(book);
                    }
                }
                case 7 -> bookImpl.showStatistics();

                case 8 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void manageBorrowers(Scanner scanner) {
        BorrowerImpl borrowerImpl = new BorrowerImpl();

        while (true) {
            System.out.println("\nChoose an operation for Borrowers:");
            System.out.println("1. Add a borrower");
            System.out.println("2. Update a borrower");
            System.out.println("3. Delete a borrower");
            System.out.println("4. Show all borrowers");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Borrower Name: ");
                    String borrowerName = scanner.nextLine();
                    System.out.print("Telephone Number: ");
                    String telephoneNumber = scanner.nextLine();
                    System.out.print("CIN: ");
                    String cin = scanner.nextLine();
                    System.out.print("Date of Birth (yyyy-MM-dd): ");
                    String dateOfBirth = scanner.nextLine();
                    Borrower borrowerToAdd = new Borrower(0, borrowerName, Integer.parseInt(telephoneNumber), cin, dateOfBirth);
                    borrowerImpl.add(borrowerToAdd);
                    System.out.println("Borrower added successfully.");
                }
                case 2 -> {
                    System.out.print("Enter ID of the borrower to update: ");
                    int borrowerIdToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("New Borrower Name: ");
                    String newBorrowerName = scanner.nextLine();
                    System.out.print("New Telephone Number: ");
                    String newTelephoneNumber = scanner.nextLine();
                    System.out.print("New CIN: ");
                    String newCin = scanner.nextLine();
                    System.out.print("New Date of Birth (yyyy-MM-dd): ");
                    String newDateOfBirth = scanner.nextLine();
                    Borrower borrowerToUpdate = new Borrower(borrowerIdToUpdate, newBorrowerName, Integer.parseInt(newTelephoneNumber), newCin, newDateOfBirth);
                    boolean updated = borrowerImpl.update(borrowerToUpdate);
                    if (updated) {
                        System.out.println("Borrower updated successfully.");
                    } else {
                        System.out.println("Borrower not found or update failed.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Cin of the borrower to delete: ");
                    String  borrowerIdToDelete = scanner.nextLine();
                    boolean deleted = borrowerImpl.delete(String.valueOf(borrowerIdToDelete));
                    if (deleted) {
                        System.out.println("Borrower deleted successfully.");
                    } else {
                        System.out.println("Borrower not found or delete failed.");
                    }
                }
                case 4 -> {
                    List<Borrower> allBorrowers = borrowerImpl.show();
                    System.out.println("All Borrowers:");
                    for (Borrower borrower : allBorrowers) {
                        System.out.println(borrower);
                    }
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    private static void manageBorrowings(Scanner scanner) {
        BorrowingImpl borrowingImpl = new BorrowingImpl();

        while (true) {
            System.out.println("\nChoose an operation for Borrowings:");
            System.out.println("1. Borrow a book");
            System.out.println("2. Return a book");
            System.out.println("3. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter ISBN of the book to borrow: ");
                    int isbnToBorrow = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the borrower's ID: ");
                    int borrowerId = scanner.nextInt();
                    scanner.nextLine();


                    Book bookToBorrow = new Book(isbnToBorrow, "", "", Book.StatusBook.Borrow);
                    Borrower borrower = new Borrower(borrowerId, "", 0, "", "");

                    borrowingImpl.borrow(bookToBorrow, borrower);
                    System.out.println("Book borrowed successfully.");
                }

                case 2 -> {
                    System.out.print("Enter ISBN of the book to return: ");
                    int isbnToReturn = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the borrower's ID: ");
                    int borrowerIdToReturn = scanner.nextInt();
                    scanner.nextLine();

                    Book bookToReturn = new Book(isbnToReturn, "", "",  Book.StatusBook.Borrow);
                    Borrower borrowerToReturn = new Borrower(borrowerIdToReturn, "", 0, "", "");

                    Borrowing updatedBorrowing = borrowingImpl.returnBook(bookToReturn, borrowerToReturn);

                    if (updatedBorrowing != null) {
                        System.out.println("\nReturn book successfully.");
                    } else {
                        System.out.println("\nReturn book failed.");
                    }
                }



                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}

