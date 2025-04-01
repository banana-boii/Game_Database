package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);
    List<Review> findByGame_GameId(Integer gameId); // Changed from Long to Integer
}
