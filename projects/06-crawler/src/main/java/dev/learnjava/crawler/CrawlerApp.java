package dev.learnjava.crawler;

/**
 * Współbieżny web crawler (etap 6: współbieżność, virtual threads).
 *
 * <p>Uruchomienie: {@code ./gradlew :crawler:run --args="https://example.com --depth 2 --variant virtual"}
 */
public final class CrawlerApp {

    private CrawlerApp() {
    }

    public static void main(String[] args) {
        // TODO: trzy warianty: CompletableFuture + fixed pool, virtual threads, StructuredTaskScope.
        System.out.println("Crawler: dostępne procesory = " + Runtime.getRuntime().availableProcessors());
    }
}
