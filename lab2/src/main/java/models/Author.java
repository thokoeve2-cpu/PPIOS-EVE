
package models;

import java.util.ArrayList;

public class Author {

    private String name;
    private String nationality;
    private int birthYear;
    private String biography;
    private ArrayList<String> publishedBooks;

    public Author(String name, String nationality,
                  int birthYear, String biography) {
        this.name = name;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.biography = biography;
        this.publishedBooks = new ArrayList<>();
    }

    public void addBook(String bookTitle) {
        publishedBooks.add(bookTitle);
    }

    public void removeBook(String bookTitle) {
        publishedBooks.remove(bookTitle);
    }

    public int countBooks() {
        return publishedBooks.size();
    }

    public void displayBooks() {
        for (String book : publishedBooks) {
            System.out.println(book);
        }
    }

    public void displayAuthor() {
        System.out.println("Author: " + name);
        System.out.println("Nationality: " + nationality);
        System.out.println("Birth Year: " + birthYear);
        System.out.println("Biography: " + biography);
    }public String getName() {
    return name;
}
}

