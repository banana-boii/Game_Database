package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByGame(Game game);
    List<Review> findByUser(User user);
}
