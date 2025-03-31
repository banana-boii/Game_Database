package devin.GameDB_backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
    // No need to override findById, as it's already provided by JpaRepository
}
