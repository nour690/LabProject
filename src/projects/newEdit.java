import java.util.Date;
import java.util.Scanner;

class Book {

    public String title, author;
    public int totalCopies, availableCopies;

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
        return title + ", " + author + ", " + availableCopies + " copies.";
    }

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
    private Book[] borrowedBooks = new Book[3];
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
                borrowedBooks[i] = borrowedBooks[--borrowCount];
                return true;
            }
        }
        return false;
    }
}

class Transaction {

    private User user;
    private Book book;
    private String type;
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
    private static final int SHELF_CAPACITY = 4;
    private static final int INITIAL_SHELVES = 10;
    private Book[][] shelves = new Book[INITIAL_SHELVES][SHELF_CAPACITY];
    private int currentShelf = 0;
    private int booksOnShelf = 0;
    private User[] users = new User[10];
    private int userCount = 0;
    private Transaction[] transactions = new Transaction[100];
    private int transactionCount = 0;

    public void addBook(String title, String author, int totalCopies) {
    if (title == null || title.isEmpty() || author == null || author.isEmpty() || totalCopies <= 0) {
        System.out.println("Invalid book details. Please try again.");
        return;
    }

    // Debug log
    System.out.println("Attempting to create a Book object with: " +
            "Title = " + title + ", Author = " + author + ", Total Copies = " + totalCopies);

    // Ensure no duplicate books exist
    for (int i = 0; i <= currentShelf; i++) {
        for (int j = 0; j < (i == currentShelf ? booksOnShelf : 4); j++) {
            if (shelves[i][j] != null &&
                shelves[i][j].getTitle().equalsIgnoreCase(title) &&
                shelves[i][j].getAuthor().equalsIgnoreCase(author)) {
                System.out.println("Book already exists in the library. the entered copies will be added to the same book.");
                shelves[i][j].availableCopies += totalCopies;
                return;
            }
        }
    }

    // Check if a new shelf is needed
    if (booksOnShelf == 4) {
        if (currentShelf == shelves.length - 1) {
            Book[][] newShelves = new Book[shelves.length + 10][4];
            System.arraycopy(shelves, 0, newShelves, 0, shelves.length);
            shelves = newShelves;
        }
        currentShelf++;
        booksOnShelf = 0;
    }

    // Add the book to the shelf
    shelves[currentShelf][booksOnShelf++] = new Book(title, author, totalCopies);
    System.out.println("Book '" + title + "' by " + author + " has been added with " + totalCopies + " copies.");
}



    public void registerUser(String name, String email) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getEmail().equalsIgnoreCase(email)) {
                System.out.println("A user with this email already exists.");
                return;
            }
        }

        if (userCount == users.length) {
            User[] newUsers = new User[users.length + 10];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            users = newUsers;
        }

        users[userCount++] = new User(name, email);
        System.out.println("User '" + name + "' has been registered.");
    }

    public Book findBook(String title, String author) {
        for (int i = 0; i <= currentShelf; i++) {
            for (int j = 0; j < (i == currentShelf ? booksOnShelf : 4); j++) {
                if (shelves[i][j].getTitle().equalsIgnoreCase(title) && shelves[i][j].getAuthor().equalsIgnoreCase(author)) {
                    return shelves[i][j];
                }
            }
        }
        System.out.println("Book not found.");
        return null;
    }

    public User findUser(String email) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getEmail().equalsIgnoreCase(email)) {
                return users[i];
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public boolean borrowBook(String email, String title, String author) {
        User user = findUser(email);
        Book book = findBook(title, author);

//        if (user != null && book != null && user.borrowBook(book)) {
//            if (transactionCount == transactions.length) {
//                Transaction[] newTransactions = new Transaction[transactions.length + 100];
//                System.arraycopy(transactions, 0, newTransactions, 0, transactions.length);
//                transactions = newTransactions;
//            }
//            transactions[transactionCount++] = new Transaction(user, book, "Borrow");
//            return true;
//        }
          if (user != null && book != null && user.borrowBook(book)) {
                if (transactionCount == transactions.length) {
                    Transaction[] newTransactions = new Transaction[transactions.length + 100];
                    System.arraycopy(transactions, 0, newTransactions, 0, transactions.length);
                    transactions = newTransactions;
            }
            transactions[transactionCount++] = new Transaction(user, book, "Borrow");
            System.out.println("Book borrowed successfully! Remaining copies: " + book.availableCopies);
            return true;
        }



        return false;
    }

    public boolean returnBook(String email, String title, String author) {
        User user = findUser(email);
        Book book = findBook(title, author);

        if (user != null && book != null && user.returnBook(book)) {
            if (transactionCount == transactions.length) {
                Transaction[] newTransactions = new Transaction[transactions.length + 100];
                System.arraycopy(transactions, 0, newTransactions, 0, transactions.length);
                transactions = newTransactions;
            }
            transactions[transactionCount++] = new Transaction(user, book, "Return");
            System.out.println("Book returned successfully! Remaining copies: " + book.availableCopies);
            return true;
        }
        return false;
    }

    public void listBooks() {
        if (currentShelf == 0 && booksOnShelf == 0) {
            System.out.println("No books available in the library.");
            return;
        }

        for (int i = 0; i <= currentShelf; i++) {
            for (int j = 0; j < (i == currentShelf ? booksOnShelf : 4); j++) {
                System.out.println(shelves[i][j].getDetails());
            }
        }
    }

    public void listTransactions() {
        if (transactionCount == 0) {
            System.out.println("No transactions recorded.");
            return;
        }

        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

public class newEdit {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        for (;;) {
            System.out.println("\nLibrary Management System: ");
            System.out.println("1. Add a New Book");
            System.out.println("2. Register a New User");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Display All Books");
            System.out.println("6. View Transactions");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter total copies: ");
                    int copies = scanner.nextInt();
                    library.addBook(title, author, copies);
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    library.registerUser(name, email);
                    break;

                case 3:
                    System.out.print("Enter user email: ");
                    String borrowerEmail = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String borrowTitle = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String borrowAuthor = scanner.nextLine();
                    if (library.borrowBook(borrowerEmail, borrowTitle, borrowAuthor)) {
                        System.out.println("Book '" + borrowTitle + "' has been borrowed.");
                    } else {
                        System.out.println("Could not borrow book. Check availability or user details.");
                    }
                    break;

                case 4:
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

                case 5:
                    library.listBooks();
                    break;

                case 6:
                    library.listTransactions();
                    break;

                case 7:
                    System.out.println("Program exited successfully.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Wrong input, try again.");
            }
        }
    }
}    