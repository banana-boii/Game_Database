package devin.GameDB_backend;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Saved_Games")
@IdClass(SavedGameId.class) // Composite key class
public class SavedGame implements Serializable {

    @Id
    private Long userId; // Must match SavedGameId field name and type

    @Id
    private Long gameId; // Must match SavedGameId field name and type

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;

    @Column(nullable = false, updatable = false)
    private Timestamp savedAt;

    // Constructors
    public SavedGame() {}

    public SavedGame(Long userId, Long gameId, User user, Game game, Timestamp savedAt) {
        this.userId = userId;
        this.gameId = gameId;
        this.user = user;
        this.game = game;
        this.savedAt = savedAt;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
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

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }
}
