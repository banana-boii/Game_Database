package devin.GameDB_backend;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Saved_Games")
public class SavedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_game_id")
    private Long savedGameId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    // Getters and Setters
    public Long getSavedGameId() {
        return savedGameId;
    }

    public void setSavedGameId(Long savedGameId) {
        this.savedGameId = savedGameId;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
