package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedGameRepository extends JpaRepository<SavedGame, Long> {
    boolean existsByUserAndGame(User user, Game game);

    void deleteByUserAndGame(User user, Game game);

    List<SavedGame> findByUser(User user);

    // Find all saved games for a specific user
    List<SavedGame> findByUserId(Long userId);

    // Find all favorite games for a specific user
    List<SavedGame> findFavoritesByUserId(Long userId);
}
