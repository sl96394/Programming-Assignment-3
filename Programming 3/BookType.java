public class BookType {
    private String title;
    private String isbn;
    private String publisher;
    private int year;
    private double price;
    private int quantity;
    private String author;

    public BookType() {}

    public BookType(String title, String isbn, String publisher,
                    int year, double price, int quantity, String author) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
        this.author = author;
    }

    public String getTitle() { return title; }
    public int getQuantity() { return quantity; }

    public String toString() {
        return title;
    }
}
