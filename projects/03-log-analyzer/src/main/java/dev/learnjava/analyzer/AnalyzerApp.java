package dev.learnjava.analyzer;

/**
 * Analizator logów/transakcji (etap 3: lambdy i Stream API).
 *
 * <p>Uruchomienie: {@code ./gradlew :log-analyzer:run --args="top-customers data.csv"}
 */
public final class AnalyzerApp {

    private AnalyzerApp() {
    }

    public static void main(String[] args) {
        // TODO: Map<String, Function<Stream<Row>, Report>> z raportami wybieranymi po nazwie.
        System.out.println("Log analyzer: podaj nazwę raportu i ścieżkę do pliku CSV.");
    }
}
