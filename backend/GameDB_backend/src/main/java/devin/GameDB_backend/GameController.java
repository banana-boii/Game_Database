package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

        // Map each game to a GameDTO with header image, all images, and the first video
        Page<GameDTO> gameDTOs = games.map(game -> {
            String headerImage = game.getHeaderImage(); // Fetch the header image
            List<String> images = screenshotRepository.findAllByGameId(game.getGameId())
                    .stream()
                    .map(Screenshot::getImageUrl)
                    .collect(Collectors.toList());
            String firstVideo = movieRepository.findFirstByGameId(game.getGameId())
                    .map(Movie::getVideoUrl)
                    .orElse(null);

            return new GameDTO(game, headerImage, images, firstVideo);
        });

        return ResponseEntity.ok(gameDTOs);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<GameDTO>> getFeaturedGames() {
        List<Game> games = gameRepository.findAll().stream()
                .filter(game -> {
                    if (game.getReleaseDate() != null) {
                        LocalDate releaseDate = game.getReleaseDate().toLocalDate();
                        return releaseDate.isAfter(LocalDate.now().minusYears(1));
                    }
                    return false;
                })
                .sorted((g1, g2) -> Integer.compare(g2.getRecommendations(), g1.getRecommendations()))
                .limit(5)
                .collect(Collectors.toList());

        List<GameDTO> featuredGames = games.stream().map(game -> {
            String headerImage = game.getHeaderImage();
            List<String> images = screenshotRepository.findAllByGameId(game.getGameId())
                    .stream()
                    .map(Screenshot::getImageUrl)
                    .collect(Collectors.toList());
            String firstVideo = movieRepository.findFirstByGameId(game.getGameId())
                    .map(Movie::getVideoUrl)
                    .orElse(null);

            return new GameDTO(game, headerImage, images, firstVideo);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(featuredGames);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<GameDTO>> getPopularGames() {
        List<Game> games = gameRepository.findAll().stream()
                .sorted((g1, g2) -> Integer.compare(g2.getPositive(), g1.getPositive()))
                .limit(8)
                .collect(Collectors.toList());

        List<GameDTO> popularGames = games.stream().map(game -> {
            String headerImage = game.getHeaderImage();
            return new GameDTO(game, headerImage, List.of(), null); // Only include header image
        }).collect(Collectors.toList());

        return ResponseEntity.ok(popularGames);
    }

    @GetMapping("/top-sellers")
    public ResponseEntity<List<GameDTO>> getTopSellers() {
        // Use 'positive' as a proxy for top sellers
        List<Game> games = gameRepository.findAll().stream()
                .sorted((g1, g2) -> Integer.compare(g2.getPositive(), g1.getPositive()))
                .limit(10)
                .collect(Collectors.toList());

        List<GameDTO> topSellers = games.stream().map(game -> {
            String headerImage = game.getHeaderImage();
            return new GameDTO(game, headerImage, List.of(), null);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(topSellers);
    }

    @GetMapping("/most-played")
    public ResponseEntity<List<GameDTO>> getMostPlayed() {
        // Use 'averagePlaytimeForever' as a proxy for most played
        List<Game> games = gameRepository.findAll().stream()
                .sorted((g1, g2) -> Integer.compare(g2.getAveragePlaytimeForever(), g1.getAveragePlaytimeForever()))
                .limit(10)
                .collect(Collectors.toList());

        List<GameDTO> mostPlayed = games.stream().map(game -> {
            String headerImage = game.getHeaderImage();
            return new GameDTO(game, headerImage, List.of(), null);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(mostPlayed);
    }

    @GetMapping("/new-releases")
    public ResponseEntity<List<GameDTO>> getNewReleases() {
        List<Game> games = gameRepository.findAll().stream()
                .filter(game -> {
                    if (game.getReleaseDate() != null) {
                        LocalDate releaseDate = game.getReleaseDate().toLocalDate();
                        return releaseDate.isAfter(LocalDate.now().minusMonths(3));
                    }
                    return false;
                })
                .limit(10)
                .collect(Collectors.toList());

        List<GameDTO> newReleases = games.stream().map(game -> {
            String headerImage = game.getHeaderImage();
            return new GameDTO(game, headerImage, List.of(), null);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(newReleases);
    }

    @PostMapping
    public ResponseEntity<String> addGame(@RequestBody Game game) {
        gameRepository.save(game);
        return ResponseEntity.ok("Game added successfully!");
    }
}

