/*import java.util.ArrayList;
import java.util.List;

class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

class User {
    private String name;
    private String email;
    private List<Book> borrowedBooks;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() < 3) {
            borrowedBooks.add(book);
            return true;
        } else {
            System.out.println("Cannot borrow more than 3 books at a time.");
            return false;
        }
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}
*/
import java.util.*;

class Book {
    private final String title;
    private final String author;
    private int availableCopies;
    private int totalCopies;

    public Book(String title, String author, int totalCopies) {
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void addCopies(int copies) {
        this.totalCopies += copies;
        this.availableCopies += copies;
    }

    public boolean borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    @Override
    public String toString() {
        return title + ", " + author + ", " + availableCopies + " copies available.";
    }
}

class User {
    private final String name;
    private final String email;
    private final List<Book> borrowedBooks;
    private static final int MAX_BORROW_LIMIT = 3;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() < MAX_BORROW_LIMIT && book.borrowBook()) {
            borrowedBooks.add(book);
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.returnBook();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}

class Transaction {
    private User user;
    private Book book;
    private String transactionType;
    private Date transactionDate;

    public Transaction(User user, Book book, String transactionType) {
        this.user = user;
        this.book = book;
        this.transactionType = transactionType;
        this.transactionDate = new Date();
    }

    @Override
    public String toString() {
        return transactionType + ": " + book.getTitle() + " by " + user.getEmail() + " on " + transactionDate;
    }
}

class Library {
    private final List<Book> books;
    private final List<User> users;
    private final List<Transaction> transactions;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public boolean addBook(String title, String author, int copies) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                book.addCopies(copies);
                return true;
            }
        }
        books.add(new Book(title, author, copies));
        return true;
    }

    public boolean registerUser(String name, String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        users.add(new User(name, email));
        return true;
    }

    public boolean borrowBook(String email, String title) {
        User user = findUserByEmail(email);
        Book book = findBookByTitle(title);
        if (user != null && book != null) {
            if (user.borrowBook(book)) {
                transactions.add(new Transaction(user, book, "Borrow"));
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(String email, String title) {
        User user = findUserByEmail(email);
        Book book = findBookByTitle(title);
        if (user != null && book != null) {
            if (user.returnBook(book)) {
                transactions.add(new Transaction(user, book, "Return"));
                return true;
            }
        }
        return false;
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    private Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}

public class LabProject2 {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Add a New Book");
            System.out.println("2. Register a New User");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Display all Books");
            System.out.println("6. View Transactions");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Total Copies: ");
                    int copies = scanner.nextInt();
                    scanner.nextLine();
                    library.addBook(title, author, copies);
                    System.out.println("Book added successfully.");
                    break;
                case 2:
                    System.out.print("Enter User Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter User Email: ");
                    String email = scanner.nextLine();
                    if (library.registerUser(name, email)) {
                        System.out.println("User registered successfully.");
                    } else {
                        System.out.println("Email already registered.");
                    }
                    break;
                case 3:
                    System.out.print("Enter User Email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter Book Title: ");
                    title = scanner.nextLine();
                    if (library.borrowBook(email, title)) {
                        System.out.println("Book borrowed successfully.");
                    } else {
                        System.out.println("Failed to borrow book. Check availability or user limit.");
                    }
                    break;
                case 4:
                    System.out.print("Enter User Email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter Book Title: ");
                    title = scanner.nextLine();
                    if (library.returnBook(email, title)) {
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Failed to return book. Check user records.");
                    }
                    break;
                case 5:
                    library.displayBooks();
                    break;
                case 6:
                    library.displayTransactions();
                    break;
                case 7:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
