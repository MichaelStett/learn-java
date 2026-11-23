# 04: Wspólny moduł, testy i jakość

**Etap 4: testowanie, jakość i build.** Szczegóły w [PLAN.md](../../PLAN.md).

Repozytorium jest od początku jednym wielomodułowym buildem Gradle, więc w tym etapie
nie scalasz projektów, tylko **wydzielasz wspólny kod** i podnosisz jakość.

## Wymagania
- Wydziel do `common` kod współdzielony przez 01–03 (np. zapis/odczyt plików, parsowanie argumentów).
  W modułach-konsumentach dodaj `implementation(project(":common"))`.
- Warstwę zapisu ukryj za interfejsem i testuj logikę z mockami Mockito (`@ExtendWith(MockitoExtension.class)`).
- W `buildSrc/src/main/kotlin/learn.java-conventions.gradle.kts`:
  - plugin `jacoco` + `jacocoTestCoverageVerification` z progiem 80%,
  - plugin `checkstyle` (lub SpotBugs/Error Prone) i poprawienie wszystkich ostrzeżeń.
- Workflow GitHub Actions `.github/workflows/build.yml`: `./gradlew build` na każdym pushu.

## Kryteria ukończenia
- [ ] `./gradlew build` przechodzi lokalnie i w CI.
- [ ] Pokrycie ≥ 80% w modułach 01–04.
- [ ] Testy parametryzowane (`@ParameterizedTest` + `@CsvSource`/`@MethodSource`).
