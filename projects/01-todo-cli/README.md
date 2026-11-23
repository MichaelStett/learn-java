# 01: Todo CLI

**Etap 1: składnia i system typów.** Szczegóły w [PLAN.md](../../PLAN.md#etap-1-składnia-i-system-typów-czyli-różnice-15-tygodnia).

## Wymagania
- Komendy: `add <tytuł> [--priority HIGH] [--tag x]`, `list`, `done <id>`, `remove <id>`, `filter --tag <x>`.
- Model: `record Task`, `enum Priority` z polami/metodami, `sealed interface Command` + rekordy jako implementacje.
- Obsługa komend przez `switch` z pattern matchingiem (bez `instanceof`-drabinek).
- Zapis/odczyt do pliku tekstowego (`Files.readAllLines` / `Files.write`), z obsługą `IOException`.
- Co najmniej jeden własny wyjątek checked i jeden unchecked z uzasadnieniem wyboru w komentarzu.

## Kryteria ukończenia
- [ ] Wszystkie komendy działają z `./gradlew :todo-cli:run --args="..."`.
- [ ] `switch` po `Command` nie ma gałęzi `default` (kompilator pilnuje kompletności dzięki `sealed`).
- [ ] Brak porównań `String`/`Integer` przez `==`.
- [ ] Testy jednostkowe parsera komend.

## Uruchomienie
```
./gradlew :todo-cli:run --args="add 'Kupić mleko' --priority HIGH"
./gradlew :todo-cli:test
```
