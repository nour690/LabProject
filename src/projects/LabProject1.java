import java.util.Scanner;
class Book {
    private String Title, Author ;
    private int AvailableCopies, TotalCopies ;

    public void addCopies(int copies) {
        this.TotalCopies += copies;
        this.AvailableCopies += copies;
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
    
}
class User {
    String name, email;
    int[] borrowedBooks ;
}
class Transaction {
    Book user ;
}
class Library{

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
