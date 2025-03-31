package devin.GameDB_backend;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*; // Ensure correct JPA imports
import jakarta.validation.constraints.Email; // Ensure correct validation imports
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    private String phone;

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid") // Add validation annotation
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Age is required") // Add validation annotation
    private Integer age;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(columnDefinition = "TEXT")
    private String bio; // User profile bio

    private String avatarUrl; // Profile picture

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedGame> favoriteGames = new ArrayList<>();

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for phone
    public String getPhone() {
        return phone;
    }

    // Setter for phone
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter for age
    public Integer getAge() {
        return age;
    }

    // Setter for age
    public void setAge(Integer age) {
        this.age = age;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for passwordHash
    public String getPasswordHash() {
        return passwordHash;
    }

    // Setter for passwordHash
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for userId
    public Long getUserId() {
        return userId;
    }

    // Setter for userId
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter for bio
    public String getBio() {
        return bio;
    }

    // Setter for bio
    public void setBio(String bio) {
        this.bio = bio;
    }

    // Getter for avatarUrl
    public String getAvatarUrl() {
        return avatarUrl;
    }

    // Setter for avatarUrl
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    // Getter for favoriteGames
    public List<SavedGame> getFavoriteGames() {
        return favoriteGames;
    }

    // Setter for favoriteGames
    public void setFavoriteGames(List<SavedGame> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }
}
