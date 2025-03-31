package devin.GameDB_backend;

import jakarta.persistence.*;

@Entity
@Table(name = "Screenshots") // Match the table name in the database
public class Screenshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screenshot_id")
    private Integer screenshotId;

    @Column(name = "game_id", nullable = false)
    private Integer gameId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    // Getters and Setters
    public Integer getScreenshotId() {
        return screenshotId;
    }

    public void setScreenshotId(Integer screenshotId) {
        this.screenshotId = screenshotId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
