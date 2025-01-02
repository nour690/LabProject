import java.util.Scanner;
import java.util.Date;
class Book {
    private String Title, Author ;
    private int AvailableCopies, TotalCopies ;

    public Book(String title, String author, int totalCopies) {
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
    
     public void setTitle(String title){
        this.Title = title ; 
    }
    public String getTitle(){
        return Title; 
    } 
    public void setAuthor(String author){
        this.Author = author ; 
    }
    public String getAuthor(){
        return Author ; 
    }
    public void setAvailableCopies(int availableCopies){
        this.AvailableCopies = availableCopies ; 
    }
    public int getAvailableCopies(){
        return AvailableCopies; 
    }
    public void setTotalCopies(int totalCopies){
        this.TotalCopies = totalCopies ; 
    }
    public int getTotalCopies(){
        return TotalCopies ; 
    }
    
    public boolean borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }else
        return false;
    }
}
class User {
    String name, email;
    int[] borrowedBooks ;
}
class Transaction {
    Book user ;
    
}
public class LabProject1 {
    public static void main(String[] args) {
     Scanner input = new Scanner (System.in);
     for (;;){
     
        System.out.println("Library Management System:");
        System.out.println("1. Add a New Book");
        System.out.println("2. Register a New User");
        System.out.println("3. Borrow a Book");
        System.out.println("4. Return a Book");
        System.out.println("5. Display all Books");
        System.out.println("6. View Transactions");
        System.out.println("7. Exit");
        System.out.println("Choose an option: ");
        
        int option = input.nextInt ();
        switch (option){
            case 1: System.out.print("Enter book title: ");
            
        
        }
     }
        
    }
}
