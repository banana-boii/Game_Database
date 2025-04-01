package devin.GameDB_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private SavedGameRepository savedGameRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRemoveFavorite_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Integer gameId = 100;
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.removeFavorite(userId, gameId);
        });

        assertEquals("404 NOT_FOUND \"User not found\"", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(gameRepository, savedGameRepository);
    }

    @Test
    void testRemoveFavorite_GameNotFound() {
        // Arrange
        Long userId = 1L;
        Integer gameId = 100;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.removeFavorite(userId, gameId);
        });

        assertEquals("404 NOT_FOUND \"Game not found\"", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).findById(gameId);
        verifyNoInteractions(savedGameRepository);
    }

    @Test
    void testRemoveFavorite_Success() {
        // Arrange
        Long userId = 1L;
        Integer gameId = 100;
        User user = new User();
        Game game = new Game();
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(java.util.Optional.of(game));

        // Act
        ResponseEntity<String> response = userController.removeFavorite(userId, gameId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Game removed from favorites.", response.getBody());
        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).findById(gameId);
        verify(savedGameRepository, times(1)).deleteByUserAndGame(user, game);
    }
}