# 06: Współbieżny web crawler

**Etap 6: współbieżność.** Szczegóły w [PLAN.md](../../PLAN.md).

Moduł ma włączone `--enable-preview` (kompilacja, testy, `run`), bo `StructuredTaskScope` jest w Javie 25 w wersji preview.

## Wymagania
- Start od URL, limit głębokości, ograniczenie do domeny, limit równoległych żądań (`Semaphore`).
- Warianty wybierane flagą `--variant`:
  - `future`: `CompletableFuture` + `Executors.newFixedThreadPool`,
  - `virtual`: `Executors.newVirtualThreadPerTaskExecutor()` i zwykły blokujący kod,
  - `structured`: `StructuredTaskScope`.
- Wyniki w `ConcurrentHashMap`, postęp raportowany przez producenta–konsumenta na `BlockingQueue`.
- Poprawna obsługa `InterruptedException` i zamykanie executorów (try-with-resources).

## Kryteria ukończenia
- [ ] Porównanie czasu i czytelności trzech wariantów (zapisz wnioski w tym README).
- [ ] Testy na lokalnym serwerze (np. `com.sun.net.httpserver.HttpServer` lub WireMock) zamiast prawdziwego internetu.
- [ ] Ctrl+C / timeout przerywa crawl bez wiszących wątków.

## Wnioski
_(uzupełnij po implementacji)_
