import java.util.*;
class Book {
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public Book(String title, String author, int totalCopies) {
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
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

    public String getDetails() {
        return title + " by " + author + " available copies " + availableCopies + "from the total copies" + totalCopies;
    }
}

class User {
    private String name;
    private String email;
    private Book[] borrowedBooks = new Book[3]; // Max 3 books
    private int borrowCount = 0;

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

    public boolean borrowBook(Book book) {
        if (borrowCount < 3 && book.borrowBook()) {
            borrowedBooks[borrowCount++] = book;
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        for (int i = 0; i < borrowCount; i++) {
            if (borrowedBooks[i] == book) {
                book.returnBook();
                borrowedBooks[i] = borrowedBooks[--borrowCount]; // Shift remaining books
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

    public Transaction(User user, Book book, String type) {
        this.user = user;
        this.book = book;
        this.type = type;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return type + " - " + book.getDetails() + " by " + user.getName() + " on " + date;
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

    for (Book book : books) {
        if (book.getDetails().contains(title) && book.getDetails().contains(author)) {
            System.out.println("Book already exists in the library.");
            return;
        }
    }

    books.add(new Book(title, author, totalCopies));
    System.out.println("Book added successfully!");
}


    public void registerUser(String name, String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("A user with this email already exists.");
                return;
            }
        }
        users.add(new User(name, email));
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getDetails().contains(title)) {
                return book;
            }
        }
        System.out.println("Book not found.");
        return null;
    }

    public User findUser(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public boolean borrowBook(String email, String title) {
        User user = findUser(email);
        Book book = findBook(title);
        if (user != null && book != null && user.borrowBook(book)) {
            transactions.add(new Transaction(user, book, "Borrow"));
            return true;
        }
        return false;
    }

    public boolean returnBook(String email, String title) {
        User user = findUser(email);
        Book book = findBook(title);
        if (user != null && book != null && user.returnBook(book)) {
            transactions.add(new Transaction(user, book, "Return"));
            return true;
        }
        return false;
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        for (Book book : books) {
            System.out.println(book.getDetails());
        }
    }

    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

public class chatGPT {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Register User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. List All Books");
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
                    System.out.println("Book added successfully!");
                    break;

                case 2: // Register a new user
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    library.registerUser(name, email);
                    System.out.println("User registered successfully!");
                    break;

                case 3: // Borrow a book
                    System.out.print("Enter user email: ");
                    String borrowerEmail = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String borrowTitle = scanner.nextLine();
                    if (library.borrowBook(borrowerEmail, borrowTitle)) {
                        System.out.println("Book borrowed successfully!");
                    } else {
                        System.out.println("Could not borrow book. Check availability or user details.");
                    }
                    break;

                case 4: // Return a book
                    System.out.print("Enter user email: ");
                    String returnEmail = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String returnTitle = scanner.nextLine();
                    if (library.returnBook(returnEmail, returnTitle)) {
                        System.out.println("Book returned successfully!");
                    } else {
                        System.out.println("Could not return book. Check user and book details.");
                    }
                    break;

                case 5: // List all books
                    System.out.println("\nAvailable Books:");
                    library.listBooks();
                    break;

                case 6: // View all transactions
                    System.out.println("\nTransaction History:");
                    library.listTransactions();
                    break;

                case 7: // Exit
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}