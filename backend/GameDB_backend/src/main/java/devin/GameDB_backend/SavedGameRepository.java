package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedGameRepository extends JpaRepository<SavedGame, SavedGameId> {
    boolean existsByUserAndGame(User user, Game game);

    void deleteByUserAndGame(User user, Game game);

    List<SavedGame> findByUser(User user);

    List<SavedGame> findByUser_UserId(Long userId);

    List<SavedGame> findFavoritesByUser_UserId(Long userId);
}
