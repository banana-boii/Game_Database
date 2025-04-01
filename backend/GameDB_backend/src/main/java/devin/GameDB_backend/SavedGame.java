package devin.GameDB_backend;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "saved_games")
public class SavedGame {

    @EmbeddedId
    private SavedGameId id;

    @ManyToOne
    @MapsId("userId") // Maps the userId part of the composite key
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("gameId") // Maps the gameId part of the composite key
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "saved_at", nullable = false, updatable = false)
    private Timestamp savedAt;

    // Default constructor
    public SavedGame() {}

    // Constructor
    public SavedGame(SavedGameId id, User user, Game game, Timestamp savedAt) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.savedAt = savedAt;
    }

    // Getters and Setters
    public SavedGameId getId() {
        return id;
    }

    public void setId(SavedGameId id) {
        this.id = id;
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
