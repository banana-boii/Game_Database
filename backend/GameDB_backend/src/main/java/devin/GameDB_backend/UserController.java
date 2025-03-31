package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SavedGameRepository savedGameRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Validation logic...
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getPassword() == null || loginRequest.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        User user = userRepository.findByUsername(loginRequest.getUsername())
                              .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }

        if (!loginRequest.getPassword().equals(user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        String token = "dummy-jwt-token"; // Replace with actual token generation logic if needed
        return ResponseEntity.ok(token);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Endpoint is working!";
    }

    @PostMapping("/{userId}/reviews")
    public ResponseEntity<String> addReview(@PathVariable Long userId,
                                            @RequestParam Integer gameId,
                                            @RequestBody Review reviewData) {
        User user = userRepository.findById(userId)
                      .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                      .orElseThrow(() -> new RuntimeException("Game not found"));
        reviewData.setUser(user);
        reviewData.setGame(game);
        reviewData.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reviewRepository.save(reviewData);
        return ResponseEntity.ok("Review added successfully.");
    }

    @GetMapping("/reviews/game/{gameId}")
    public ResponseEntity<List<Review>> getReviewsByGame(@PathVariable Integer gameId) {
        Game game = gameRepository.findById(gameId)
                      .orElseThrow(() -> new RuntimeException("Game not found"));
        List<Review> reviews = reviewRepository.findByGame(game);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{userId}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable Long userId, @RequestParam Integer gameId) {
        User user = userRepository.findById(userId)
                      .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                      .orElseThrow(() -> new RuntimeException("Game not found"));

        if (savedGameRepository.existsByUserAndGame(user, game)) {
            return ResponseEntity.badRequest().body("Game already in favorites.");
        }

        SavedGame favorite = new SavedGame(user, game, new Timestamp(System.currentTimeMillis()));
        savedGameRepository.save(favorite);
        return ResponseEntity.ok("Game added to favorites.");
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Game>> getFavorites(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                      .orElseThrow(() -> new RuntimeException("User not found"));

        List<SavedGame> savedGames = savedGameRepository.findByUser(user);
        List<Game> favoriteGames = savedGames.stream().map(SavedGame::getGame).collect(Collectors.toList());

        return ResponseEntity.ok(favoriteGames);
    }

    @DeleteMapping("/{userId}/favorites")
    public ResponseEntity<String> removeFavorite(@PathVariable Long userId,
                                                 @RequestParam Integer gameId) {
        User user = userRepository.findById(userId)
                      .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                      .orElseThrow(() -> new RuntimeException("Game not found"));

        savedGameRepository.deleteByUserAndGame(user, game);
        return ResponseEntity.ok("Game removed from favorites.");
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/library")
    public ResponseEntity<String> addGameToLibrary(@PathVariable Long userId, @RequestParam Integer gameId) {
        User user = userRepository.findById(userId)
                      .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                      .orElseThrow(() -> new RuntimeException("Game not found"));

        if (savedGameRepository.existsByUserAndGame(user, game)) {
            return ResponseEntity.badRequest().body("Game already in library.");
        }

        SavedGame savedGame = new SavedGame(user, game, new Timestamp(System.currentTimeMillis()));
        savedGameRepository.save(savedGame);
        return ResponseEntity.ok("Game added to library.");
    }

    @GetMapping("/{userId}/library")
    public ResponseEntity<List<SavedGame>> getUserLibrary(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                      .orElseThrow(() -> new RuntimeException("User not found"));
        List<SavedGame> library = savedGameRepository.findByUser(user);
        return ResponseEntity.ok(library);
    }
}

package devin.GameDB_backend;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Saved_Games")
@IdClass(SavedGameId.class) // Composite key class
public class SavedGame implements Serializable {

    @Id
    private Long userId;

    @Id
    private Long gameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;

    @Column(nullable = false, updatable = false)
    private Timestamp savedAt;

    // Default constructor
    public SavedGame() {}

    // Constructor with parameters
    public SavedGame(User user, Game game, Timestamp savedAt) {
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
