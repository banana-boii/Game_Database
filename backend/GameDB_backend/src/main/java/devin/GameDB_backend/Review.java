package devin.GameDB_backend;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Reviews") // Match the table name in the SQL file
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id") // Match the column name in the SQL file
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Match the foreign key column
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false) // Match the foreign key column
    private Game game;

    private Integer rating;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
