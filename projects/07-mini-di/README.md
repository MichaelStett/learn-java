# 07: Mini kontener DI + mini framework testowy

**Etap 7: JVM pod maską, refleksja i adnotacje.** Szczegóły w [PLAN.md](../../PLAN.md).

## Wymagania
- Własne adnotacje `@Component`, `@Inject`, `@Singleton`/`@Prototype` (`@Retention(RUNTIME)`).
- Kontener (`dev.learnjava.di`): skanowanie pakietu, wstrzykiwanie przez konstruktor, wykrywanie cykli z czytelnym komunikatem.
- Runner testów (`dev.learnjava.testrunner`): własne `@Test`, `@BeforeEach`, raport passed/failed/czas.
- Benchmark JMH w `src/jmh/java`: `Method.invoke` vs `MethodHandle` vs wywołanie bezpośrednie.
- Profilowanie crawlera z etapu 6 w JFR:
  `./gradlew :crawler:run` z `-XX:StartFlightRecording=duration=60s,filename=crawl.jfr`, analiza w JDK Mission Control.

## Kryteria ukończenia
- [ ] Testy (JUnit) kontenera: singleton, prototype, cykl, brakująca zależność.
- [ ] Wyniki JMH i wnioski z JFR zapisane w tym README.

## Wyniki
_(uzupełnij)_
