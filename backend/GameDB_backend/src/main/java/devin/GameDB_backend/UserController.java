package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; // Import this
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        validateUser(user);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Directly compare the provided password with the stored password
        if (!loginRequest.getPassword().equals(user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        // Generate a placeholder token (or return a success message)
        String token = "dummy-token"; // Replace with actual token logic if needed
        return ResponseEntity.ok(token);
    }

    @PostMapping("/{userId}/reviews")
    public ResponseEntity<String> addReview(@PathVariable Long userId,
                                            @RequestParam Integer gameId,
                                            @RequestBody Review reviewData) {
        System.out.println("Received gameId: " + gameId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        reviewData.setUser(user);
        reviewData.setGame(game);
        reviewData.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reviewRepository.save(reviewData);
        return ResponseEntity.ok("Review added successfully.");
    }

    @PostMapping("/{userId}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable Long userId,
                                              @RequestParam Integer gameId) {
        return addGameToCollection(userId, gameId, "favorites");
    }

    @PostMapping("/{userId}/library")
    public ResponseEntity<String> addGameToLibrary(@PathVariable Long userId,
                                                   @RequestParam Integer gameId) {
        return addGameToCollection(userId, gameId, "library");
    }

    private ResponseEntity<String> addGameToCollection(Long userId, Integer gameId, String collectionType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        if (savedGameRepository.existsByUserAndGame(user, game)) {
            return ResponseEntity.badRequest().body("Game already in " + collectionType + ".");
        }

        SavedGame savedGame = new SavedGame();
        savedGameRepository.save(savedGame);
        return ResponseEntity.ok("Game added to " + collectionType + ".");
    }

    @Transactional // Add this annotation
    @DeleteMapping("/{userId}/favorites")
    public ResponseEntity<String> removeFavorite(@PathVariable Long userId,
                                                 @RequestParam Integer gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        savedGameRepository.deleteByUserAndGame(user, game);
        return ResponseEntity.ok("Game removed from favorites.");
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Game>> getUserFavorites(@PathVariable Long userId) {
        List<SavedGame> favoriteGames = savedGameRepository.findFavoritesByUserId(userId);
        List<Game> games = favoriteGames.stream()
                .map(SavedGame::getGame)
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{userId}/library")
    public ResponseEntity<List<Game>> getUserLibrary(@PathVariable Long userId) {
        List<SavedGame> savedGames = savedGameRepository.findByUserId(userId);
        List<Game> games = savedGames.stream()
                .map(SavedGame::getGame)
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required.");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required.");
        }
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }
        if (user.getAge() == null || user.getAge() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Age must be a positive number.");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required.");
        }
    }
}
