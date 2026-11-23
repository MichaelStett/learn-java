# 05: Klient publicznego API z cache

**Etap 5: I/O, czas, HTTP, JSON.** Szczegóły w [PLAN.md](../../PLAN.md).

Domyślnie skonfigurowane API NBP (kursy walut, bez klucza): https://api.nbp.pl
Alternatywy: GitHub REST API, Open-Meteo.

## Wymagania
- `java.net.http.HttpClient` (synchronicznie), mapowanie JSON → rekordy przez Jackson.
- Cache odpowiedzi na dysku (`java.nio.file.Files`) z TTL (`Instant`, `Duration`) + `LruCache` z modułu `collections` w pamięci.
- Konfiguracja z `application.properties`, logowanie SLF4J + Logback (`logback.xml`).
- Czytelne błędy dla 4xx/5xx i timeoutów.

## Kryteria ukończenia
- [ ] Testy z WireMock: poprawna odpowiedź, 404, 500, timeout.
- [ ] Testy cache: trafienie, wygaśnięcie TTL (wstrzykiwany `java.time.Clock` zamiast `Instant.now()`).
- [ ] Brak użycia `java.util.Date`.
