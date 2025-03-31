package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenshotRepository extends JpaRepository<Screenshot, Integer> {
    Optional<Screenshot> findFirstByGameId(Integer gameId);

    // Add this method to fetch all screenshots for a game
    List<Screenshot> findAllByGameId(Integer gameId);
}
