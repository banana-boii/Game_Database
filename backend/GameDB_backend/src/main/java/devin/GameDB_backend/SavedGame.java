package devin.GameDB_backend;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Saved_Games") // ensure this matches your table name
public class SavedGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    // Constructors, getters, and setters
}
