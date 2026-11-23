# 09: Mini e-commerce (mikroserwisy), czyli projekt finałowy

**Etap 9: backend produkcyjny.** Szczegóły w [PLAN.md](../../PLAN.md).

Serwisy **nie są jeszcze dołączone do builda**. Gdy zaczniesz ten etap:
1. W każdym katalogu serwisu utwórz `build.gradle.kts` wzorowany na `projects/08-library-api`.
2. Dopisz serwisy do mapy `modules` w głównym `settings.gradle.kts`, np.
   `"ecommerce-catalog" to "projects/09-ecommerce/catalog"`.
3. Dodaj potrzebne biblioteki do `gradle/libs.versions.toml` (Spring Kafka, Spring Security OAuth2 Resource Server,
   Spring Cloud Gateway + BOM Spring Cloud, Resilience4j, Micrometer Tracing / OpenTelemetry).

## Struktura
```
09-ecommerce/
├── compose.yaml      # Kafka, PostgreSQL (osobna baza na serwis), Keycloak, Jaeger/Tempo, Prometheus, Grafana
├── gateway/          # Spring Cloud Gateway + walidacja JWT
├── catalog/          # produkty, stany magazynowe
├── orders/           # zamówienia, Saga/Outbox, zdarzenie OrderPlaced
├── payments/         # symulowane płatności, zdarzenie PaymentCompleted
└── notifications/    # konsument zdarzeń, "wysyłka" e-maili do logu
```

## Kryteria ukończenia
- [ ] Przepływ: złożenie zamówienia → rezerwacja → płatność → powiadomienie, w tym ścieżka kompensacji przy odrzuconej płatności.
- [ ] Ślad jednego żądania widoczny przez wszystkie serwisy w Jaeger/Tempo.
- [ ] Resilience4j (retry + circuit breaker) na wywołaniu orders → catalog.
- [ ] Testy integracyjne na Testcontainers (Kafka + PostgreSQL) w CI.
- [ ] Jeden serwis zbudowany jako GraalVM native image, z porównaniem czasu startu i zużycia pamięci w tym README.
