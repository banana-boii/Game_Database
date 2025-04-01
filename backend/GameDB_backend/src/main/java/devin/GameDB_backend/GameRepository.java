package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    // No need to override findById, as it's already provided by JpaRepository
    // Add new method for searching games by title
    List<Game> findByNameContainingIgnoreCase(String title);
}
