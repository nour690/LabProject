import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String Title, Author;
    private int AvailableCopies, TotalCopies;

    public Book(String title, String author, int totalCopies) {
        this.Title = title;
        this.Author = author;
        this.TotalCopies = totalCopies;
        this.AvailableCopies = totalCopies; // Varsayılan olarak tüm kopyalar mevcut
    }

    // Getter ve Setter metodları
    public void setTitle(String title) {
        this.Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAvailableCopies(int availableCopies) {
        this.AvailableCopies = availableCopies;
    }

    public int getAvailableCopies() {
        return AvailableCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.TotalCopies = totalCopies;
    }

    public int getTotalCopies() {
        return TotalCopies;
    }
}

class Library {
    private ArrayList<Book> bookList;

    // Constructor
    public Library() {
        this.bookList = new ArrayList<>();
    }

    // Kitap ekleme metodu
   public void addBookInteractive() {
        System.out.print("Kitap Adı: ");
        String title = input.nextLine();
        System.out.print("Yazar: ");
        String author = input.nextLine();
        System.out.print("Toplam Kopya Sayısı: ");
        int totalCopies = input.nextInt();
        input.nextLine(); // Scanner'daki newline karakterini temizlemek için

        // Yeni kitap oluştur ve listeye ekle
        Book newBook = new Book(title, author, totalCopies);
        bookList.add(newBook);

        System.out.println("Kitap başarıyla eklendi: " + newBook.getTitle());
    }

    // Tüm kitapları listeleme metodu
    public void displayBooks() {
        if (bookList.isEmpty()) {
            System.out.println("Kütüphanede kitap yok.");
            return;
        }
        System.out.println("Kütüphanedeki Kitaplar:");
        for (Book book : bookList) {
            System.out.println("- " + book.getTitle() + " by " + book.getAuthor() + 
                               " (" + book.getAvailableCopies() + "/" + book.getTotalCopies() + ")");
        }
    }

    

    // Kitap listesine erişim (isteğe bağlı)
    public ArrayList<Book> getBookList() {
        return bookList;
    }
}
public static void showMenu(Library library) {
    Scanner input = new Scanner(System.in);
    int choice;

    do {
        System.out.println("\nLibrary Management System:");
        System.out.println("1. Add a New Book");
        System.out.println("2. Register a New User");
        System.out.println("3. Borrow a Book");
        System.out.println("4. Return a Book");
        System.out.println("5. Display all Books");
        System.out.println("6. View Transactions");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
        choice = input.nextInt();
        input.nextLine(); // Scanner'daki newline karakterini temizlemek için

        switch (choice) {
            case 1:
                library.addBookInteractive(); // Kitap ekleme işlemi
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                library.displayBooks(); // Tüm kitapları göster
                break;
            case 6:
                break;
            case 7:
                break;
            default:
        }
    } while (choice != 7);
}

public class LabProject1 {
    public static void main(String[] args) {
        Library library = new Library();
        showMenu(library)
    }
}
