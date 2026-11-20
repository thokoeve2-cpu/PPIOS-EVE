package models;

public class BookReview {

    private String reviewId;
    private Book book;
    private Customer customer;
    private int rating;
    private String comment;
    private String reviewDate;
    private int helpfulVotes;

    public BookReview(String reviewId, Book book,
                      Customer customer, int rating,
                      String comment, String reviewDate) {

        this.reviewId = reviewId;
        this.book = book;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.helpfulVotes = 0;
    }

    public void updateComment(String newComment) {
        if (newComment != null && !newComment.trim().isEmpty()) {
            comment = newComment;
        }
    }

    public void updateRating(int newRating) {
        if (newRating >= 1 && newRating <= 5) {
            rating = newRating;
        }
    }

    public void markHelpful() {
        helpfulVotes++;
    }

    public boolean isPositiveReview() {
        return rating >= 4;
    }

    public boolean isValidRating() {
        return rating >= 1 && rating <= 5;
    }

    public void displayReview() {
        System.out.println("Review ID: " + reviewId);
        System.out.println("Book: " + book.getTitle());
        System.out.println("Customer: " + customer.getFullName());
        System.out.println("Rating: " + rating + "/5");
        System.out.println("Comment: " + comment);
        System.out.println("Review Date: " + reviewDate);
        System.out.println("Helpful Votes: " + helpfulVotes);
    }

    public String getReviewId() {
        return reviewId;
    }

    public int getRating() {
        return rating;
    }

    public int getHelpfulVotes() {
        return helpfulVotes;
    }
}