package devin.GameDB_backend;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews") // ensure this matches your table name
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors, getters, and setters
}
