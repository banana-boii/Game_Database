package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavedGameRepository extends JpaRepository<SavedGame, SavedGameId> {
    boolean existsByUserAndGame(User user, Game game);

    void deleteByUserAndGame(User user, Game game);

    List<SavedGame> findByUser(User user);
}
