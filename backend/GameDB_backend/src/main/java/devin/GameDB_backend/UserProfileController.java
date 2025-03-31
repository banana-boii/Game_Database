package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    // Get user profile
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    // Update user profile
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserProfile(
        @PathVariable Long userId,
        @RequestBody User updatedUser) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUser.getName());
        user.setPhone(updatedUser.getPhone());
        user.setEmail(updatedUser.getEmail());
        user.setBio(updatedUser.getBio());
        user.setAvatarUrl(updatedUser.getAvatarUrl());

        userRepository.save(user);
        return ResponseEntity.ok("Profile updated successfully.");
    }
}
