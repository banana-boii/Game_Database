package devin.GameDB_backend;

import java.util.List;

public class GameDTO {
    private Game game;
    private List<String> images;
    private String firstVideo;

    public GameDTO(Game game, List<String> images, String firstVideo) {
        this.game = game;
        this.images = images;
        this.firstVideo = firstVideo;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
}
