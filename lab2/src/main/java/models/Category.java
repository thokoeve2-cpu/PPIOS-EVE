
package models;

import java.util.ArrayList;

public class Category {

    private String categoryName;
    private String description;
    private int categoryId;
    private ArrayList<String> books;

    public Category(int categoryId, String categoryName,
                    String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.books = new ArrayList<>();
    }

    public void addBook(String title) {
        books.add(title);
    }

    public void removeBook(String title) {
        books.remove(title);
    }

    public boolean containsBook(String title) {
        return books.contains(title);
    }

    public int getBookCount() {
        return books.size();
    }

    public void displayCategory() {
        System.out.println("Category ID: " + categoryId);
        System.out.println("Category: " + categoryName);
        System.out.println("Description: " + description);
        System.out.println("Number of books: " + books.size());
    }
    public String getCategoryName() {
    return categoryName;
}
}
