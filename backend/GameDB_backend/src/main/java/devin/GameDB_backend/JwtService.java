package devin.GameDB_backend;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "your_secure_secret_key"; // Replace with a secure key

    public String generateToken(String username) {
        try {
            return Jwts.builder()
                    .setSubject(username)
                    .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating JWT token");
        }
    }

    public String extractUsername(String token) {
        // Parse the token and extract the username (subject)
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
