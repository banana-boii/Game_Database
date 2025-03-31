package devin.GameDB_backend;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Saved_Games")
public class SavedGame {

    @EmbeddedId
    private SavedGameId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "saved_at", nullable = false, updatable = false)
    private Timestamp savedAt;

    // Constructors
    public SavedGame() {}

    public SavedGame(User user, Game game, Timestamp savedAt) {
        this.id = new SavedGameId(user.getUserId(), game.getGameId());
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
        if (this.id == null) {
            this.id = new SavedGameId();
        }
        this.id.setUserId(user.getUserId());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        if (this.id == null) {
            this.id = new SavedGameId();
        }
        this.id.setGameId(game.getGameId());
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }
}
