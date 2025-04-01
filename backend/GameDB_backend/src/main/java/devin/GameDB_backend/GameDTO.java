package devin.GameDB_backend;

import java.sql.Timestamp;
import java.util.List;

public class GameDTO {
    private Game game;
    private String headerImage;
    private List<String> images;
    private String firstVideo;
    private Timestamp savedAt;

    // Constructor without savedAt
    public GameDTO(Game game, String headerImage, List<String> images, String firstVideo) {
        this.game = game;
        this.headerImage = headerImage;
        this.images = images;
        this.firstVideo = firstVideo;
    }

    // Constructor with savedAt
    public GameDTO(Game game, String headerImage, List<String> images, String firstVideo, Timestamp savedAt) {
        this.game = game;
        this.headerImage = headerImage;
        this.images = images;
        this.firstVideo = firstVideo;
        this.savedAt = savedAt;
    }

    // Getters and Setters
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getFirstVideo() {
        return firstVideo;
    }

    public void setFirstVideo(String firstVideo) {
        this.firstVideo = firstVideo;
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }
}
