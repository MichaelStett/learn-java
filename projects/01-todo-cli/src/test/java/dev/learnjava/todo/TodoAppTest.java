package dev.learnjava.todo;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

class TodoAppTest {

    @Test
    void runsWithoutArguments() {
        // Test dymny: sprawdza, że build i JUnit działają. Zastąp prawdziwymi testami.
        assertThatCode(() -> TodoApp.main(new String[0])).doesNotThrowAnyException();
    }
}
