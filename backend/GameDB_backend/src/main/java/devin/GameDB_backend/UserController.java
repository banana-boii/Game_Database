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

    @Autowired
    private JwtService jwtService; // Inject JwtService

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        validateUser(user);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login request received for username: " + loginRequest.getUsername()); // Debugging log

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        System.out.println("User found: " + user.getUsername()); // Debugging log

        if (!loginRequest.getPassword().equals(user.getPasswordHash())) {
            System.out.println("Invalid credentials for username: " + loginRequest.getUsername()); // Debugging log
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        String token = jwtService.generateToken(user.getUsername());
        System.out.println("Generated token: " + token); // Debugging log
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

        SavedGame savedGame = new SavedGame(user, game, new Timestamp(System.currentTimeMillis()));
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
    public ResponseEntity<?> getUserFavorites(@PathVariable Long userId) {
        try {
            List<SavedGame> favoriteGames = savedGameRepository.findByUser_UserId(userId);
            List<GameDTO> games = favoriteGames.stream()
                    .map(savedGame -> new GameDTO(
                            savedGame.getGame(),
                            savedGame.getGame().getHeaderImage(),
                            null, // No images for favorites
                            null  // No videos for favorites
                    ))
                    .collect(Collectors.toList());

            // Log the response for debugging
            System.out.println("Favorites Response: " + games);

            return ResponseEntity.ok(games);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"An error occurred while fetching favorites.\"}");
        }
    }

    @GetMapping("/{userId}/library")
    public ResponseEntity<?> getUserLibrary(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }

        try {
            List<SavedGame> savedGames = savedGameRepository.findByUser_UserId(userId);
            List<GameDTO> games = savedGames.stream()
                    .map(savedGame -> new GameDTO(
                            savedGame.getGame(),
                            savedGame.getGame().getHeaderImage(),
                            null, // No images for library
                            null  // No videos for library
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(games);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the library.");
        }
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
