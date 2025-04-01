package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/reviews") // Ensure this matches the endpoint path
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;


    @GetMapping
    public ResponseEntity<List<Review>> getReviewsByGame(@RequestParam Integer gameId) {
        List<Review> reviews = reviewRepository.findByGame_GameId(gameId);
        return ResponseEntity.ok(reviews);
    }
    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> addReview(@RequestBody Map<String, Object> payload) {
        try {
            // Log the received payload for debugging
            System.out.println("Received payload: " + payload);
            
            // Check if all required fields are present
            if (payload.get("userId") == null) {
                return ResponseEntity.badRequest().body("Error: userId is required");
            }
            if (payload.get("gameId") == null) {
                return ResponseEntity.badRequest().body("Error: gameId is required");
            }
            
            Long userId = Long.parseLong(payload.get("userId").toString());
            Integer gameId = Integer.parseInt(payload.get("gameId").toString());
            
            // Log the parsed values
            System.out.println("Parsed userId: " + userId);
            System.out.println("Parsed gameId: " + gameId);
            
            // Check if rating and reviewText are present
            if (payload.get("rating") == null) {
                return ResponseEntity.badRequest().body("Error: rating is required");
            }
            if (payload.get("reviewText") == null) {
                return ResponseEntity.badRequest().body("Error: reviewText is required");
            }
            
            Integer rating = Integer.parseInt(payload.get("rating").toString());
            String reviewText = payload.get("reviewText").toString();
            
            // Log the rating and reviewText
            System.out.println("Rating: " + rating);
            System.out.println("Review Text: " + reviewText);
            
            // Find the user
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            
            // Find the game
            Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + gameId));
            
            // Create and save the review
            Review review = new Review();
            review.setUser(user);
            review.setGame(game);
            review.setRating(rating);
            review.setReviewText(reviewText);
            review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            
            Review savedReview = reviewRepository.save(review);
            System.out.println("Review saved with ID: " + savedReview.getReviewId());
            
            return ResponseEntity.ok("Review added successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Print the full stack trace to the console
            return ResponseEntity.badRequest().body("Error adding review: " + e.getMessage());
        }
    }
}
