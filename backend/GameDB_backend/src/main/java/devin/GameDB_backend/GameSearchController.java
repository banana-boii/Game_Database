package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameSearchController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/search")
    public ResponseEntity<List<GameSearchResult>> searchGamesByTitle(@RequestParam String title) {
        // Log the search request
        System.out.println("Searching for games with title: " + title);
        
        // Find games that contain the search term in their name (case insensitive)
        List<Game> matchingGames = gameRepository.findByNameContainingIgnoreCase(title);
        
        // Transform to DTO objects with only the required fields
        List<GameSearchResult> results = matchingGames.stream()
                .map(game -> new GameSearchResult(
                        game.getGameId(),
                        game.getName(),
                        game.getShortDescription(),
                        game.getHeaderImage()
                ))
                .collect(Collectors.toList());
        
        System.out.println("Found " + results.size() + " matching games");
        
        return ResponseEntity.ok(results);
    }
    
    // DTO class to return only the required fields
    public static class GameSearchResult {
        private Integer gameId;
        private String title;
        private String description;
        private String headerImage;
        
        public GameSearchResult(Integer gameId, String title, String description, String headerImage) {
            this.gameId = gameId;
            this.title = title;
            this.description = description;
            this.headerImage = headerImage;
        }
        
        public Integer getGameId() {
            return gameId;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String getDescription() {
            return description;
        }
        
        public String getHeaderImage() {
            return headerImage;
        }
    }
}
