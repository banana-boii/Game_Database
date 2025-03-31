package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; // Import this
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private JwtService jwtService;

    // In-memory blacklist for tokens (use a database or cache for production)
    private final Set<String> tokenBlacklist = new HashSet<>();

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

        if (!loginRequest.getPassword().equals(user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        // Generate JWT token using the username
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        // Extract the token without the "Bearer " prefix
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Add the token to the blacklist
        tokenBlacklist.add(actualToken);

        return ResponseEntity.ok("Logged out successfully.");
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
    public ResponseEntity<List<SavedGame>> getFavorites(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        validateToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<SavedGame> favorites = savedGameRepository.findByUser(user);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/{userId}/library")
    public ResponseEntity<List<SavedGame>> getUserLibrary(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        validateToken(token);

        String username = jwtService.extractUsername(token, "secondArgument"); // Replace "secondArgument" with the actual required value
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        if (!user.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        List<SavedGame> library = savedGameRepository.findByUser(user);
        return ResponseEntity.ok(library);
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

// Add this method to the JwtService class
public String extractUsername(String token) {
    // Implement logic to extract username from the JWT token
    // Example: Decode the token and extract the username claim
    // This is a placeholder implementation
    return "decodedUsername"; // Replace with actual decoding logic
}

    private void validateToken(String token) {
        // Extract the token without the "Bearer " prefix
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Check if the token is blacklisted
        if (tokenBlacklist.contains(actualToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is blacklisted. Please log in again.");
        }
    }
}
