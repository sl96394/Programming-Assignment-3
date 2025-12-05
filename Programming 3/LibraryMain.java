import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LibraryMain {

    public static void main(String[] args) {
        ArrayList<BookType> books = new ArrayList<>();
        ArrayList<Patron> patrons = new ArrayList<>();
        HashSet<String> allCheckedOut = new HashSet<>();
        Scanner sc = new Scanner(System.in);

        try {
            loadBooks("bookData2.txt", books);
        } catch (Exception e) {
            System.out.println("Missing bookData2.txt");
            return;
        }

        boolean quit = false;
        while (!quit) {
            menu();
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": printAll(books, allCheckedOut); break;
                case "2": printAvailable(books, allCheckedOut); break;
                case "3": search(sc, books, allCheckedOut); break;
                case "4": addPatron(sc, patrons); break;
                case "5": checkout(sc, books, patrons, allCheckedOut); break;
                case "6": checkin(sc, patrons, allCheckedOut); break;
                case "7": printPatrons(patrons, allCheckedOut); break;
                case "q":
                case "Q": quit=true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    public static void loadBooks(String file, ArrayList<BookType> books) throws FileNotFoundException {
        Scanner f = new Scanner(new File(file));
        while (f.hasNextLine()) {
            String title = f.nextLine();
            if (title.isEmpty()) continue;
            String isbn = f.nextLine();
            String pub = f.nextLine();
            int year = Integer.parseInt(f.nextLine().trim());
            double price = Double.parseDouble(f.nextLine().trim());
            int qty = Integer.parseInt(f.nextLine().trim());
            String author = f.nextLine();
            books.add(new BookType(title, isbn, pub, year, price, qty, author));
        }
    }

    public static void menu() {
        System.out.println("1 Print all books");
        System.out.println("2 Print available books");
        System.out.println("3 Search available");
        System.out.println("4 Add patron");
        System.out.println("5 Check out");
        System.out.println("6 Check in");
        System.out.println("7 Print patrons");
        System.out.println("Q Quit");
    }

    public static BookType find(ArrayList<BookType> books, String t) {
        for (BookType b : books) {
            if (b.getTitle().equalsIgnoreCase(t)) return b;
        }
        return null;
    }

    public static Patron findPatron(ArrayList<Patron> p, String n) {
        for (Patron x : p) if (x.getName().equalsIgnoreCase(n)) return x;
        return null;
    }

    public static void printAll(ArrayList<BookType> b, HashSet<String> out) {
        for (BookType x : b) {
            if (out.contains(x.getTitle())) System.out.println(x.getTitle()+" (checked out)");
            else System.out.println(x.getTitle());
        }
    }

    public static void printAvailable(ArrayList<BookType> b, HashSet<String> out) {
        for (BookType x : b) {
            if (x.getQuantity()>0 && !out.contains(x.getTitle()))
                System.out.println(x.getTitle());
        }
    }

    public static void search(Scanner sc, ArrayList<BookType> b, HashSet<String> out) {
        System.out.print("Title: ");
        String t=sc.nextLine();
        BookType x=find(b,t);
        if (x!=null && x.getQuantity()>0 && !out.contains(x.getTitle()))
            System.out.println("Available");
        else System.out.println("Not available");
    }

    public static void addPatron(Scanner sc, ArrayList<Patron> p) {
        System.out.print("Name: ");
        String n=sc.nextLine();
        if (findPatron(p,n)!=null) { System.out.println("Exists"); return; }
        p.add(new Patron(n));
        Collections.sort(p);
    }

    public static void checkout(Scanner sc, ArrayList<BookType> b,
                                ArrayList<Patron> p, HashSet<String> out) {
        System.out.print("Patron: ");
        String n=sc.nextLine();
        Patron pat=findPatron(p,n);
        if (pat==null) { pat=new Patron(n); p.add(pat); Collections.sort(p); }

        System.out.print("Book: ");
        String t=sc.nextLine();
        BookType bk=find(b,t);
        if (bk==null) { System.out.println("No such book"); return; }
        if (bk.getQuantity()==0 || out.contains(bk.getTitle())) {
            System.out.println("Unavailable"); return;
        }

        pat.addBook(bk.getTitle());
        out.add(bk.getTitle());
        System.out.println("Checked out");
    }

    public static void checkin(Scanner sc, ArrayList<Patron> p,
                               HashSet<String> out) {
        System.out.print("Patron: ");
        String n=sc.nextLine();
        Patron pat=findPatron(p,n);
        if (pat==null) { System.out.println("Not found"); return; }

        System.out.print("Book: ");
        String t=sc.nextLine();
        if (pat.getCheckedOut().contains(t)) {
            pat.removeBook(t);
            out.remove(t);
            System.out.println("Checked in");
        } else System.out.println("They do not have that");
    }

    public static void printPatrons(ArrayList<Patron> p, HashSet<String> out) {
        for (Patron x : p) x.printInfo();

        System.out.println("All checked out:");
        for (String s : out) System.out.println(" - "+s);
    }
}
