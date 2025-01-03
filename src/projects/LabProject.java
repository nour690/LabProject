
import java.util.Scanner;
import java.util.Date;
import java.util.ArrayList;

/*
if book doesn't exist cant borrow
 */
class Book {

    private String title, author;
    private int totalCopies, availableCopies;

    // constructor
    public Book(String title, String author, int totalCopies) {
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        //this.availableCopies = totalCopies;
    }

    // if the book is available then it would substract 1 and return true.
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
    public int returnAvilability() { 
        return availableCopies;
    }

    public String getDetails() {
        return title + ", " + author + ", " + availableCopies + " copies.";
    }
}

class User {

    private String name;
    private String email;
    private Book[] borrowedBooksArray = new Book[3]; // Max 3 books

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    int borrowCount = 0;

    public boolean borrowBook(Book book) {
        if (borrowCount < 3 && book.borrowBook()) {
            borrowedBooksArray[borrowCount++] = book;
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        for (int i = 0; i < borrowCount; i++) {
            if (borrowedBooksArray[i] == book) {
                book.returnBook();
                borrowedBooksArray[i] = borrowedBooksArray[--borrowCount]; // Shift remaining books
                return true;
            }
        }
        return false;
    }
}

class Transaction {

    private User user;
    private Book book;
    private String type; // "Borrow" or "Return"
    private Date date;
    private int availableCopies;

    public Transaction(User user, Book book, String type , int availableCopies) {
        this.user = user;
        this.book = book;
        this.type = type;
        this.date = new Date();
        this.availableCopies = availableCopies;
    }

     @Override
    public String toString() {
        return type + " ; " + book.getDetails() + " (Available: " + availableCopies + " copies) by " + user.getName() + " on " + date;
    }
}
 
class Library {

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void addBook(String title, String author, int totalCopies) {
        if (title == null || title.isEmpty() || author == null || author.isEmpty() || totalCopies <= 0) {
            System.out.println("Invalid book details. Please try again.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getDetails().contains(title) && book.getDetails().contains(author)) {
                System.out.println("Book already exists in the library.");
                return;
            }
        }

        books.add(new Book(title, author, totalCopies));
        System.out.println("Book '" + title + "' by " + author + " has been added with " + totalCopies + " copies");
    }

    public void registerUser(String name, String email) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("A user with this email already exists.");
                return;
            }
        }
        users.add(new User(name, email));
    }

    public Book findBook(String title, String author) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getDetails().contains(title) && book.getDetails().contains(author)) {
                return book;
            }
        }
        System.out.println("Book not found.");
        return null;
    }

    public User findUser(String email) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        System.out.println("User not found.");
        return null;
    }
    public String returnNameUser(String email) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user.getName();
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public boolean borrowBook(String email, String title, String author) {
        User user = findUser(email);
        Book book = findBook(title, author);
        //ZBook book = findBook(author);
        if (user != null && book != null && user.borrowBook(book)) {
            transactions.add(new Transaction(user, book, "Borrow",book.returnAvilability()));
            return true;
        }
        return false;
    }

    public boolean returnBook(String email, String title, String author) {
        User user = findUser(email);
        Book book = findBook(title, author);
        if (user != null && book != null && user.returnBook(book)) {
            transactions.add(new Transaction(user, book, "Return", book.returnAvilability()));
            return true;
        }
        return false;
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book.getDetails());
        }
    }

    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println(transaction);
        }
    }
}

public class LabProject {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        for (;;) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Add a New Book");
            System.out.println("2. Register a New User");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Display All Books");
            System.out.println("6. View Transactions");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Add a new book
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter total copies: ");
                    int copies = scanner.nextInt();
                    library.addBook(title, author, copies);
                    break;

                case 2: // Register a new user
                    /*System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    library.registerUser(name, email);
                    System.out.println("User '" + name + "' has been registered.");
                    break;*/
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    // Boş giriş kontrolü
                    if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
                        System.out.println("Name or email cannot be empty. Please try again.");
                        break; // Kullanıcı girişini yeniden başlatmak için döngüden çıkın
                    }

                    library.registerUser(name, email);
                    System.out.println("User '" + name + "' has been registered.");
                    break;

                case 3: // Borrow a book
                    System.out.print("Enter user email: ");
                    String borrowerEmail = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String borrowTitle = scanner.nextLine();
                    System.out.print("Enter book Author: ");
                    String Author = scanner.nextLine();
                    if (library.borrowBook(borrowerEmail, borrowTitle, Author)) {
                        System.out.println("Book '" + borrowTitle + "' has been borrowed by " + library.returnNameUser(borrowerEmail));
                    } else {
                        System.out.println("Could not borrow book. Check availability or user details.");
                    }
                    break;

                case 4: // Return a book
                    System.out.print("Enter user email: ");
                    String returnEmail = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String returnTitle = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String returnAuthor = scanner.nextLine();
                    if (library.returnBook(returnEmail, returnTitle, returnAuthor)) {
                        System.out.println("Book returned successfully!");
                    } else {
                        System.out.println("Could not return book. Check user and book details.");
                    }
                    break;

                case 5: // List all books
                    library.listBooks();
                    break;

                case 6: // View all transactions
                    library.listTransactions();
                    break;

                case 7: // Exit
                    System.out.println("Program exited successfully.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Wrong input, try again.");
            }
        }
    }
}
