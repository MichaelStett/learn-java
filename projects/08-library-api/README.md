# 08: REST API biblioteki (Spring Boot)

**Etap 8: Spring Boot vs ASP.NET Core.** Szczegóły w [PLAN.md](../../PLAN.md).

## Uruchomienie
Wymaga Docker Desktop.
```
./gradlew :library-api:bootRun      # sam startuje PostgreSQL z compose.yaml
./gradlew :library-api:test         # Testcontainers; testy pomijane bez Dockera
```
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health

## Struktura (do utworzenia)
```
dev.learnjava.library
├── book/       BookController, BookService, BookRepository, Book (encja), BookDto (record)
├── user/
├── loan/
└── common/     GlobalExceptionHandler (@RestControllerAdvice + ProblemDetail)
```
Pakietuj po funkcjonalności (feature folders), nie po warstwach.

## Wymagania
- Encje: użytkownicy, książki, wypożyczenia (1:N i N:M). Schemat tylko przez Flyway (`db/migration`).
- CRUD + wyszukiwanie z `Pageable`, walidacja `@Valid`, błędy jako `ProblemDetail`.
- Profile `dev` / `test` / `prod` (`application-{profile}.yml`).
- Rekordy jako DTO, encje jako klasy. Wstrzykiwanie przez konstruktor.

## Kryteria ukończenia
- [ ] `@WebMvcTest` dla kontrolerów, `@DataJpaTest` + Testcontainers dla repozytoriów, `@SpringBootTest` end-to-end.
- [ ] Brak problemu N+1 na liście wypożyczeń (sprawdź logi SQL: `spring.jpa.show-sql=true`).
- [ ] Udokumentowane API w OpenAPI.

## Uwaga o wersji
Szkielet używa Spring Boot 3.5.x. Spring Boot 4 (Spring Framework 7, Jackson 3, modularne startery)
jest już dostępny. Migracja na 4.x to dobre ćwiczenie po ukończeniu projektu.
