package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews") // Ensure this matches the endpoint path
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public ResponseEntity<List<Review>> getReviewsByGame(@RequestParam Long gameId) {
        List<Review> reviews = reviewRepository.findByGame_GameId(gameId);
        return ResponseEntity.ok(reviews);
    }
}
