package devin.GameDB_backend;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*; // Ensure correct JPA imports
import java.sql.Timestamp;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(nullable = false)
    private String name;
    
    private String phone;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private Integer age;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String passwordHash;
    
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    
    // Getter for email
    public String getEmail() {
        return email;
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

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for passwordHash
    public String getPasswordHash() {
        return passwordHash;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Getters and setters...
}
