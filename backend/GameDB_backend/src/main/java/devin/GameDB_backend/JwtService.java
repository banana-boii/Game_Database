package devin.GameDB_backend;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    // Generate a user-specific secret key (e.g., based on username or user ID)
    private String getUserSpecificSecretKey(String username) {
        // Example: Append a static salt to the username to create a unique key
        String salt = "static_salt_value"; // Replace with a secure salt
        return username + salt;
    }

    public String generateToken(String username) {
        try {
            String secretKey = getUserSpecificSecretKey(username); // Generate user-specific secret key
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date()) // Set the current timestamp
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // Use the user-specific secret key
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating JWT token");
        }
    }

    public String extractUsername(String token, String username) {
        try {
            String secretKey = getUserSpecificSecretKey(username); // Generate user-specific secret key
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error extracting username from JWT token");
        }
    }
}
