package devin.GameDB_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "devin.GameDB_backend") // Ensure the base package is correct
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
