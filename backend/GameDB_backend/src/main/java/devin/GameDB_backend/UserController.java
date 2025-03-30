package devin.GameDB_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import devin.GameDB_backend.User;
import devin.GameDB_backend.UserRepository;
import devin.GameDB_backend.LoginRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required.");
        }
    
        // Validate that email is provided
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }
        if (user.getAge() == null) {
            return ResponseEntity.badRequest().body("Age is required.");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required.");
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }
        // You can add further validations here if needed
        
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        // Validate user credentials and generate a token if needed.
        return ResponseEntity.ok("Login successful!");
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Endpoint is working!";
    }
}
