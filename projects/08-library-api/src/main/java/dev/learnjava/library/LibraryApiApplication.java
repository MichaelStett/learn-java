package dev.learnjava.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * REST API wypożyczalni/biblioteki (etap 8: Spring Boot).
 *
 * <p>Uruchomienie (wymaga Dockera): {@code ./gradlew :library-api:bootRun}
 * <br>Swagger UI: http://localhost:8080/swagger-ui.html
 */
@SpringBootApplication
public class LibraryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplication.class, args);
    }
}
