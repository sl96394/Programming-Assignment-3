import java.util.HashSet;
import java.util.Iterator;

public class Patron implements Comparable<Patron> {
    private static int nextId = 1;
    private String name;
    private String id;
    private HashSet<String> checkedOut = new HashSet<>();

    public Patron(String name) {
        this.name = name;
        this.id = "P" + nextId++;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public HashSet<String> getCheckedOut() { return checkedOut; }

    public void addBook(String title) { checkedOut.add(title); }
    public void removeBook(String title) { checkedOut.remove(title); }

    public void printInfo() {
        System.out.println(name + " (ID: " + id + ")");
        if (checkedOut.isEmpty()) System.out.println("  No books checked out.");
        else {
            System.out.println("  Books:");
            Iterator<String> it = checkedOut.iterator();
            while (it.hasNext()) System.out.println("   - " + it.next());
        }
    }

    public int compareTo(Patron other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
