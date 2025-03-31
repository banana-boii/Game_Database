package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ScreenshotRepository screenshotRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/list")
    public ResponseEntity<Page<GameDTO>> getAllGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> games = gameRepository.findAll(pageable);

        // Map each game to a GameDTO with all images and the first video
        Page<GameDTO> gameDTOs = games.map(game -> {
            List<String> images = screenshotRepository.findAllByGameId(game.getGameId())
                    .stream()
                    .map(Screenshot::getImageUrl)
                    .collect(Collectors.toList());
            String firstVideo = movieRepository.findFirstByGameId(game.getGameId())
                    .map(Movie::getVideoUrl)
                    .orElse(null);

            return new GameDTO(game, images, firstVideo);
        });

        return ResponseEntity.ok(gameDTOs);
    }

    @PostMapping
    public ResponseEntity<String> addGame(@RequestBody Game game) {
        gameRepository.save(game);
        return ResponseEntity.ok("Game added successfully!");
    }
}

