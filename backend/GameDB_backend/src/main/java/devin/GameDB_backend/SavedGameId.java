package devin.GameDB_backend;

import java.io.Serializable;
import java.util.Objects;

public class SavedGameId implements Serializable {

    private Long userId; // Updated to match the provided structure
    private Long gameId; // Updated to match the provided structure

    // Default constructor
    public SavedGameId() {}

    // Parameterized constructor
    public SavedGameId(Long userId, Long gameId) {
        this.userId = userId;
        this.gameId = gameId;
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

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedGameId that = (SavedGameId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, gameId);
    }
}