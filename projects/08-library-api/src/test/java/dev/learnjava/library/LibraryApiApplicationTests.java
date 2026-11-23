package dev.learnjava.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

// Test jest pomijany, gdy Docker nie działa, więc `./gradlew build` przejdzie i bez niego.
@Testcontainers(disabledWithoutDocker = true)
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class LibraryApiApplicationTests {

    @Test
    void contextLoads() {
    }
}
