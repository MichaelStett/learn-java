package dev.learnjava.todo;

import java.util.Arrays;

/**
 * Punkt wejścia aplikacji Todo CLI (etap 1).
 *
 * <p>Uruchomienie: {@code ./gradlew :todo-cli:run --args="list"}
 */
public final class TodoApp {

    private TodoApp() {
    }

    public static void main(String[] args) {
        // TODO: sparsuj args do sealed interface Command i obsłuż przez switch z pattern matchingiem.
        System.out.println("Todo CLI: argumenty = " + Arrays.toString(args));
    }
}
