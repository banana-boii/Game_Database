package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavedGameRepository extends JpaRepository<SavedGame, Long> {
    List<SavedGame> findByUser(User user);
}
