
package models;

public class Book {


    private String title;
    private String isbn;
    private String language;
    private int publicationYear;
    private int pages;
    private double price;
    private int quantity;

    private Author author;
    private Publisher publisher;
    private Category category;
    private BookFormat format;

    public Book(String title, String isbn, String language,
                int publicationYear, int pages,
                double price, int quantity) {

        this.title = title;
        this.isbn = isbn;
        this.language = language;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.price = price;
        this.quantity = quantity;
    }

    public void assignAuthor(Author author) {
        this.author = author;
    }

    public void assignPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void assignCategory(Category category) {
        this.category = category;
    }

    public void assignFormat(BookFormat format) {
        this.format = format;
    }

    public void displayBook() {
        displayCoreDetails();
        displayRelatedDetails();
    }

    private void displayCoreDetails() {
        System.out.println("Title: " + title);
        System.out.println("ISBN: " + isbn);
        System.out.println("Language: " + language);
        System.out.println("Year: " + publicationYear);
        System.out.println("Pages: " + pages);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + quantity);
    }

    private void displayRelatedDetails() {
        if (author != null) System.out.println("Author: " + author.getName());
        if (publisher != null) System.out.println("Publisher: " + publisher.getName());
        if (category != null) System.out.println("Category: " + category.getCategoryName());
        if (format != null) System.out.println("Format: " + format.getFormatName());
    }

    public void updatePrice(double newPrice) {
        if (newPrice > 0) {
            price = newPrice;
        }
    }

    public void addStock(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public void removeStock(int amount) {
        if (amount > 0 && amount <= quantity) {
            quantity -= amount;
        }
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public double calculateTotalValue() {
        return price * quantity;
    }

    public String getTitle() {
        return title;
    }
    public String getIsbn() {
        return this.isbn;
    }

    public int getQuantity() {
        return this.quantity;
    }
    public double getPrice() {
    return price;
}
}
