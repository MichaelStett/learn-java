# Plan nauki Javy dla doświadczonego programisty C#/.NET

> Cel: dojść do poziomu, na którym swobodnie tworzysz produkcyjne aplikacje backendowe w Javie (Spring Boot), rozumiesz JVM i ekosystem, a przy tym nie przenosisz do Javy nawyków z C#, które tam nie pasują.
>
> Wersja docelowa: **Java 25 (LTS)**. Jeśli w pracy trafisz na starszą, najczęściej będzie to 17 albo 21 i różnice są niewielkie.
> Szacowany czas: **12–16 tygodni** przy ok. 8–10 h tygodniowo.

---

## Jak korzystać z planu

- Każdy etap ma tabelę **C# → Java**, listę **pułapek**, na które łatwo wpaść z nawykami z .NET, oraz **projekt**, który zrobisz wyłącznie wiedzą z tego i wcześniejszych etapów.
- Projekty są w katalogu `projects/` (szkielet opisany w [README.md](README.md)), a każdy ma swoje README z wymaganiami i kryteriami ukończenia. Przy późniejszych etapach wracaj do starych projektów i je ulepszaj (testy, współbieżność, REST).
- Polecane narzędzia: **IntelliJ IDEA** (Community wystarczy do etapu 7, do Springa przyda się Ultimate albo VS Code z Extension Pack for Java), JDK **Eclipse Temurin 25** lub **Microsoft Build of OpenJDK**, **SDKMAN**/`winget` do zarządzania JDK.

---

## Etap 0: Ekosystem i narzędzia (2–3 dni)

| .NET / C# | Java |
|---|---|
| .NET SDK / runtime | JDK / JRE (JVM) |
| `dotnet` CLI | `javac`, `java`, `jar`, `jshell` + Maven/Gradle |
| MSBuild + `.csproj` | **Maven** (`pom.xml`) lub **Gradle** (`build.gradle.kts`) |
| NuGet | Maven Central (oba narzędzia build pobierają z niego zależności) |
| Assembly (`.dll`) | `.jar` (archiwum ZIP z plikami `.class`) |
| Namespace | Package (musi odpowiadać strukturze katalogów!) |
| Solution z wieloma projektami | Multi-module Maven / multi-project Gradle |
| C# Interactive / `dotnet script` | `jshell` |
| IL + CLR + JIT | Bytecode + JVM (HotSpot) + JIT |
| Native AOT | GraalVM Native Image |

**Do przyswojenia:**
- Konwencje nazewnictwa: `camelCase` dla metod (`getName()`, nie `GetName()`), pakiety małymi literami w odwrotnej notacji domenowej (`com.michal.todo`).
- Jedna publiczna klasa najwyższego poziomu na plik, a nazwa pliku = nazwa klasy.
- Standardowy układ katalogów Maven/Gradle: `src/main/java`, `src/test/java`, `src/main/resources`.
- Classpath, a dla chętnych system modułów (`module-info.java`, JPMS).
- Od Javy 22+ możesz uruchomić `java Main.java` bez kompilacji, a od 25 pisać „compact source files” (`void main() { ... }`) do szybkich eksperymentów.

**Mini-projekt 0: „Hello, toolchain”**
Wygeneruj projekt Gradle (`gradle init`) oraz Maven (`mvn archetype:generate`), dodaj jedną zależność z Maven Central (np. `commons-lang3`), zbuduj fat-JAR i uruchom go `java -jar`. Porównaj oba narzędzia i wybierz jedno do dalszej nauki. Polecam **Gradle z Kotlin DSL** albo **Maven**, bo Maven dominuje w korporacjach.

---

## Etap 1: Składnia i system typów, czyli różnice (1,5 tygodnia)

| C# | Java |
|---|---|
| `struct`, typy wartościowe użytkownika | **Brak** (tylko 8 typów prymitywnych; Project Valhalla jeszcze w drodze) |
| `int?`, `Nullable<T>` | Typy opakowujące `Integer`, `Long`... (mogą być `null`) |
| Nullable reference types (`string?`) | Brak w języku. `Optional<T>` dla wartości zwracanych, adnotacje `@Nullable` (JSpecify) |
| `uint`, `ulong`, `byte` bez znaku | **Brak typów bez znaku** (`byte` ma znak: -128..127) |
| `decimal` | `BigDecimal` (klasa, bez operatorów!) |
| Properties `{ get; set; }` | Gettery/settery (`getName()`/`setName()`) lub **`record`** |
| `record` / `record struct` | `record` (niemutowalny, tylko klasa) |
| `using (...)` / `using var` | `try (var r = ...) { }` (try-with-resources, `AutoCloseable`) |
| `readonly` pole / zmienna lokalna | `final` |
| `sealed class` (brak dziedziczenia) | `final class` |
| — | `sealed interface/class ... permits A, B` (zamknięta hierarchia) |
| `const` | `static final` |
| `internal` | package-private (brak modyfikatora!) lub moduły JPMS |
| `protected` | `protected` = także **cały pakiet** |
| `virtual` / `override` | Wszystkie metody są domyślnie wirtualne; `@Override` to adnotacja (używaj zawsze) |
| `enum` (liczby) | `enum` to pełne klasy: pola, konstruktory, metody, `EnumMap`, `EnumSet` |
| Klasa zagnieżdżona | `static class` = jak w C#; **niestatyczna** klasa wewnętrzna trzyma referencję do instancji zewnętrznej |
| Extension methods | **Brak**, zamiast nich statyczne klasy utility |
| `ref` / `out` / `in` | **Brak**, wszystko jest przekazywane przez wartość (referencje też) |
| Przeciążanie operatorów | **Brak** |
| `string` interpolation `$"..."` | `String.format`, `"...".formatted(...)`, `+` (string templates wycofano) |
| Raw string literals `"""` | Text blocks `"""` |
| `switch` expression + pattern matching | `switch` z `->`, pattern matching dla typów i record patterns (`case Point(var x, var y) ->`) |
| `is Type t` | `instanceof Type t` |
| `var` | `var` (tylko zmienne lokalne) |
| `object` | `Object` |
| `Exception` (wszystkie nieweryfikowane) | **Checked** (`Exception`) vs **unchecked** (`RuntimeException`) |

**Pułapki:**
1. `==` na `String` i na `Integer` porównuje **referencje**. Zawsze używaj `.equals()`. Na przykład `Integer a = 1000, b = 1000; a == b` daje `false`!
2. Przy przesłonięciu `equals()` zawsze przesłoń też `hashCode()` (rekordy robią to za Ciebie).
3. Checked exceptions: musisz je złapać albo zadeklarować w `throws`. W nowoczesnym kodzie często opakowuje się je w `RuntimeException`/`UncheckedIOException`.
4. Brak `nameof`, brak `default(T)` i brak inicjalizatorów obiektów `new X { A = 1 }`, więc używaj konstruktorów, rekordów albo wzorca Builder.
5. Rozpakowanie (`unboxing`) `null` z `Integer` do `int` rzuca `NullPointerException`.

**Projekt 1: Menedżer zadań CLI (Todo)**
- Komendy `add`, `list`, `done`, `remove`, `filter --tag` z argumentów `main`.
- Model: `record Task(...)`, `enum Priority` z polami i metodami, `sealed interface Command` z rekordami jako implementacjami, obsługiwanymi przez `switch` z pattern matchingiem.
- Zapis/odczyt do pliku tekstowego (prosto, przez `Files.readAllLines`/`Files.write`).
- Własne wyjątki: jeden checked, jeden unchecked. Świadomie zdecyduj, który gdzie.

---

## Etap 2: Generyki i kolekcje (1 tydzień)

| C# | Java |
|---|---|
| Generyki reifikowane (`typeof(T)` działa) | **Type erasure**: `T` znika w runtime, brak `new T()`, `T[]`, `instanceof List<String>` |
| `List<int>` | Tylko `List<Integer>` (boxing!); dla wydajności `int[]` lub `IntStream` |
| `out T` / `in T` (wariancja na deklaracji) | Wildcards przy użyciu: `? extends T` (producer), `? super T` (consumer), czyli reguła **PECS** |
| `where T : IComparable<T>` | `<T extends Comparable<? super T>>` |
| `List<T>` | `List<T>` (interfejs) + `ArrayList<T>` (implementacja) |
| `Dictionary<K,V>` | `Map<K,V>` + `HashMap`, `LinkedHashMap` (kolejność), `TreeMap` (sortowanie) |
| `HashSet<T>` / `SortedSet<T>` | `HashSet` / `TreeSet` |
| `Queue<T>` / `Stack<T>` | `ArrayDeque<T>` (nie używaj `Stack`!) |
| `PriorityQueue<T,P>` | `PriorityQueue<T>` + `Comparator` |
| `ImmutableList`, `IReadOnlyList` | `List.of(...)`, `List.copyOf(...)`, `Collections.unmodifiableList(...)` |
| `IEnumerable<T>` / `yield return` | `Iterable<T>` / `Iterator<T>`. **Brak `yield`**, iterator piszesz ręcznie |
| `IComparer<T>` | `Comparator<T>` (`Comparator.comparing(Person::name).thenComparing(...)`) |
| — | `SequencedCollection` (Java 21): `getFirst()`, `getLast()`, `reversed()` |

**Pułapki:**
- `List.of()` jest niemutowalne i rzuca wyjątek przy `add`, a także nie akceptuje `null`.
- `Arrays.asList()` ma stały rozmiar, ale da się na nim wywołać `set`.
- Modyfikacja kolekcji podczas iteracji kończy się `ConcurrentModificationException` (jak w C#), więc używaj `removeIf` albo `Iterator.remove()`.
- `map.get(key)` zwraca `null` zamiast rzucać wyjątek. Przydatne metody: `getOrDefault`, `computeIfAbsent`, `merge`.

**Projekt 2: Biblioteka struktur danych + rozbudowa Todo**
- Zaimplementuj generyczny `LruCache<K, V>` (na bazie `LinkedHashMap` i drugi raz ręcznie) oraz generyczną kolejkę priorytetową z własnym `Iterator`.
- Napisz metodę `static <T extends Comparable<? super T>> T max(Collection<? extends T> c)` i zrozum, dlaczego wildcardy są potrzebne.
- W projekcie Todo dodaj indeks po tagach (`Map<String, Set<Task>>`) i sortowanie przez `Comparator` po wielu polach.

---

## Etap 3: Lambdy, interfejsy funkcyjne i Streams kontra LINQ (1,5 tygodnia)

| C# | Java |
|---|---|
| `Func<T,R>`, `Action<T>`, `Predicate<T>` | `Function<T,R>`, `Consumer<T>`, `Predicate<T>`, `Supplier<T>`, `BiFunction`, `UnaryOperator`... |
| Delegaty jako typ | **Interfejsy funkcyjne** (jedna metoda abstrakcyjna, `@FunctionalInterface`) |
| `event` / `+=` | **Brak**, stosuje się wzorzec Listener (lista callbacków) |
| Method group `Foo.Bar` | Method reference `Foo::bar`, `this::bar`, `Foo::new` |
| Domknięcie modyfikuje zmienną | Zmienne przechwytywane muszą być **effectively final** |
| `default interface methods` | `default` metody w interfejsach (od dawna, to standardowa praktyka) |

**LINQ → Stream API:**

| LINQ | Stream |
|---|---|
| `Where` | `filter` |
| `Select` | `map` (`mapToInt`, `mapToObj`...) |
| `SelectMany` | `flatMap` / `mapMulti` |
| `OrderBy().ThenBy()` | `sorted(Comparator.comparing(...).thenComparing(...))` |
| `GroupBy` | `collect(Collectors.groupingBy(...))` |
| `ToDictionary` | `collect(Collectors.toMap(...))` (rzuca wyjątek przy duplikatach klucza!) |
| `ToList()` | `toList()` (niemutowalna) / `collect(Collectors.toList())` |
| `Aggregate` | `reduce` |
| `Sum`, `Average`, `Count`, `Max` | `mapToInt(...).sum()`, `average()`, `count()`, `max(comparator)` |
| `Any`, `All` | `anyMatch`, `allMatch`, `noneMatch` |
| `First`, `FirstOrDefault` | `findFirst()` zwraca `Optional<T>` |
| `Skip`, `Take`, `TakeWhile` | `skip`, `limit`, `takeWhile`, `dropWhile` |
| `Distinct` | `distinct` |
| `Zip`, `Chunk` | Brak wbudowanych; od Java 24 `Gatherers.windowFixed(...)` |
| `Join` | Brak, zwykle przez `Map` + `flatMap` |
| `AsParallel()` (PLINQ) | `parallelStream()` |
| `IQueryable` (EF) | Brak odpowiednika w Streams (to rola JPA/jOOQ) |

**Pułapki:**
- Stream da się skonsumować **tylko raz**, nie jest to `IEnumerable`, który można iterować wielokrotnie.
- Lambdy nie mogą rzucać checked exceptions, jeśli interfejs funkcyjny ich nie deklaruje. To częsty ból przy I/O w strumieniach.
- `Optional` nie służy do pól ani parametrów, tylko do wartości zwracanych.

**Projekt 3: Analizator logów/transakcji**
- Wczytaj duży plik CSV (np. wygenerowany zbiór transakcji bankowych lub logi serwera HTTP) przez `Files.lines()`.
- Raporty: top N klientów, sumy per miesiąc (`groupingBy` + `summingDouble`/`BigDecimal` reduce), wykrywanie anomalii, histogram kodów HTTP.
- Wybór raportu przez mapę `Map<String, Function<Stream<Record>, Report>>`.
- Mechanizm „pluginów” powiadomień przez wzorzec Listener zamiast eventów.

---

## Etap 4: Testowanie, jakość i build (1 tydzień)

| .NET | Java |
|---|---|
| xUnit / NUnit | **JUnit 5 (Jupiter)** |
| `[Fact]`, `[Theory]` + `[InlineData]` | `@Test`, `@ParameterizedTest` + `@ValueSource`/`@CsvSource`/`@MethodSource` |
| FluentAssertions | **AssertJ** (`assertThat(x).isEqualTo(...)`) |
| Moq / NSubstitute | **Mockito** |
| Coverlet | JaCoCo |
| Roslyn analyzers, StyleCop | Checkstyle, SpotBugs, PMD, Error Prone, SonarLint |
| Source generators | Annotation processors, np. **Lombok** (popularny, ale kontrowersyjny; rekordy często go zastępują) |
| `dotnet test` | `./gradlew test` / `mvn test` |

**Do przyswojenia:** cykl życia Mavena (`validate → compile → test → package → verify → install → deploy`) lub zadania Gradle, scope'y zależności (`compile`/`runtime`/`test` czy też `implementation`/`testImplementation`), BOM-y do zarządzania wersjami.

**Projekt 4: Refaktoryzacja i testy**
- Repozytorium jest już jednym wielomodułowym buildem, więc wydziel wspólny kod z projektów 1–3 do modułu `common` (`projects/04-common`).
- Pokrycie testami ≥ 80% (JaCoCo), testy parametryzowane, mocki Mockito dla warstwy zapisu plików.
- Dodaj Checkstyle/SpotBugs do builda i napraw ostrzeżenia.
- GitHub Actions: build + testy na każdym pushu.

---

## Etap 5: I/O, czas, HTTP, JSON (1 tydzień)

| .NET | Java |
|---|---|
| `System.IO.File`, `Path`, `Directory` | `java.nio.file.Files`, `Path`, `Paths` |
| `StreamReader`/`Stream` | `BufferedReader`, `InputStream`/`OutputStream`, `Reader`/`Writer` |
| `DateTime`, `DateTimeOffset`, `TimeSpan` | `java.time`: `LocalDate`, `LocalDateTime`, `ZonedDateTime`, `Instant`, `Duration`, `Period` (nigdy `java.util.Date`!) |
| `HttpClient` | `java.net.http.HttpClient` |
| System.Text.Json / Newtonsoft | **Jackson** (de facto standard), Gson |
| `ILogger` | **SLF4J** (fasada) + Logback/Log4j2 |
| `appsettings.json` / `IConfiguration` | `Properties`, a w Springu `application.yml` |

**Projekt 5: Klient CLI publicznego API z cache**
- Np. klient GitHub API lub kursów walut NBP (`api.nbp.pl`) / pogody (Open-Meteo).
- `HttpClient` (synchronicznie), deserializacja JSON do rekordów przez Jackson.
- Cache odpowiedzi na dysku z TTL (`Instant`, `Duration`), używając `LruCache` z projektu 2.
- Logowanie przez SLF4J + Logback, konfiguracja z pliku `.properties`.
- Testy z mockowanym serwerem HTTP (WireMock).

---

## Etap 6: Współbieżność, czyli największa różnica koncepcyjna (1,5–2 tygodnie)

| .NET | Java |
|---|---|
| `async`/`await`, `Task<T>` | **Brak async/await**. Masz `CompletableFuture<T>` (styl kontynuacji) albo **virtual threads** (zwykły blokujący kod) |
| `Task.Run` | `ExecutorService.submit`, `CompletableFuture.supplyAsync` |
| `Task.WhenAll` / `WhenAny` | `CompletableFuture.allOf` / `anyOf`; `StructuredTaskScope` (preview) |
| `ThreadPool` | `Executors.newFixedThreadPool`, `ForkJoinPool` |
| Brak odpowiednika | **Virtual threads** (Java 21+): `Executors.newVirtualThreadPerTaskExecutor()`, `Thread.ofVirtual()` |
| `CancellationToken` | Przerwania (`Thread.interrupt()`, `InterruptedException`), `Future.cancel` |
| `lock` | `synchronized` / `ReentrantLock` |
| `SemaphoreSlim`, `ReaderWriterLockSlim` | `Semaphore`, `ReentrantReadWriteLock`, `StampedLock` |
| `Interlocked` | `AtomicInteger`, `AtomicLong`, `LongAdder`, `AtomicReference` |
| `ConcurrentDictionary`, `ConcurrentQueue` | `ConcurrentHashMap`, `ConcurrentLinkedQueue`, `BlockingQueue` |
| `Channel<T>` | `BlockingQueue<T>` (`ArrayBlockingQueue`, `LinkedBlockingQueue`) |
| `AsyncLocal<T>` | `ThreadLocal<T>`, a z virtual threads `ScopedValue` (Java 25) |
| `volatile` | `volatile` (semantyka Java Memory Model, przeczytaj o *happens-before*) |

**Kluczowa myśl:** w nowoczesnej Javie zamiast pisać „kolorowy” kod async piszesz **zwykły blokujący kod na virtual threads**, a JVM sama zawiesza je przy I/O. To odwrotna filozofia niż w .NET.

**Pułapki:**
- Nie łap i nie ignoruj `InterruptedException`. Przywróć flagę `Thread.currentThread().interrupt()`.
- `ExecutorService` trzeba zamknąć (od Java 19 jest `AutoCloseable`, więc używaj try-with-resources).
- Nie poolujesz virtual threads. Każde zadanie dostaje nowy wątek.

**Projekt 6: Współbieżny web crawler / downloader**
- Crawler zaczynający od URL, ograniczony głębokością i domeną, z limitem równoległości (`Semaphore`).
- Wersja A z `CompletableFuture` + fixed thread pool, wersja B z virtual threads, wersja C z `StructuredTaskScope` (preview). Porównaj czytelność i wydajność.
- Wyniki zbierane w `ConcurrentHashMap`, a postęp raportowany przez wątek producenta–konsumenta na `BlockingQueue`.
- Alternatywnie: przyspiesz analizator z projektu 3 przez równoległe przetwarzanie wielu plików.

---

## Etap 7: JVM pod maską, refleksja i adnotacje (1 tydzień)

| .NET | Java |
|---|---|
| Atrybuty `[Attr]` | Adnotacje `@Annotation` (z `@Retention`, `@Target`) |
| `System.Reflection` | `java.lang.reflect`, `MethodHandles` |
| GC (Workstation/Server, generacje) | G1 (domyślny), **ZGC** (niskie pauzy), Parallel, Shenandoah |
| dotnet-trace, dotnet-counters, PerfView | **JFR** (Java Flight Recorder) + JDK Mission Control, VisualVM, async-profiler |
| `dotnet-dump` | `jcmd`, `jmap`, `jstack`, heap dump + Eclipse MAT |
| BenchmarkDotNet | **JMH** |
| `AssemblyLoadContext` | ClassLoadery |

**Do przyswojenia:** struktura pamięci (heap, metaspace, stosy wątków), flagi `-Xmx`, `-XX:+UseZGC`, warm-up JIT (C1/C2), dlaczego mikrobenchmarki bez JMH kłamią.

**Projekt 7: Mini kontener DI + mini framework testowy**
- Własne adnotacje `@Component`, `@Inject`, `@Test`.
- Kontener skanuje pakiet, tworzy grafy zależności przez konstruktory, wykrywa cykle i obsługuje singleton/prototype.
- Mini runner testów: znajduje metody `@Test`, uruchamia je i raportuje wyniki.
- Zmierz wydajność refleksji vs `MethodHandle` w **JMH**, a projekt 6 sprofiluj w JFR.

Ten projekt świetnie przygotowuje do Springa, bo zrozumiesz, co dzieje się „pod spodem”.

---

## Etap 8: Spring Boot vs ASP.NET Core (2–3 tygodnie)

| ASP.NET Core | Spring Boot |
|---|---|
| `Program.cs`, `WebApplication.CreateBuilder` | `@SpringBootApplication` + `SpringApplication.run` |
| Wbudowany DI (`AddScoped`/`AddSingleton`) | Spring IoC: `@Component`/`@Service`/`@Repository`, `@Bean` w `@Configuration`; domyślnie **singleton** |
| `[ApiController]`, `ControllerBase` | `@RestController`, `@GetMapping`, `@PostMapping`, `@PathVariable`, `@RequestBody` |
| Minimal APIs | Spring WebFlux functional endpoints (rzadziej) / zwykłe kontrolery |
| Middleware | Filtry Servlet, `HandlerInterceptor` |
| `appsettings.{Env}.json`, `IOptions<T>` | `application-{profile}.yml`, `@ConfigurationProperties` |
| Environments | Profile (`spring.profiles.active`) |
| Data Annotations / FluentValidation | Bean Validation (`@Valid`, `@NotNull`, `@Size`) |
| `ProblemDetails` | `ProblemDetail` + `@ControllerAdvice` / `@ExceptionHandler` |
| Entity Framework Core | **Spring Data JPA + Hibernate** (lub jOOQ / Spring Data JDBC) |
| `DbContext` + `DbSet<T>` | `EntityManager` + interfejsy `JpaRepository<T, ID>` |
| LINQ to Entities | Derived queries (`findByEmailAndActiveTrue`), JPQL `@Query`, Criteria API, Querydsl |
| EF Migrations | **Flyway** / Liquibase |
| `[Authorize]`, ASP.NET Identity | **Spring Security** (`SecurityFilterChain`, `@PreAuthorize`) |
| Health checks | **Actuator** (`/actuator/health`, `/metrics`) |
| Swashbuckle / NSwag | springdoc-openapi |
| `WebApplicationFactory` (testy integracyjne) | `@SpringBootTest`, `@WebMvcTest`, `@DataJpaTest`, MockMvc |
| Testcontainers for .NET | **Testcontainers** (oryginał jest javowy) |
| Kestrel | Embedded Tomcat (domyślnie) / Jetty / Undertow |

**Pułapki:**
- Leniwe ładowanie w JPA i problem N+1 (jak w EF, ale łatwiej wpaść): `LazyInitializationException`, więc używaj `JOIN FETCH` i `@EntityGraph`.
- `@Transactional` działa przez proxy, więc wywołanie metody z tej samej klasy **pomija** transakcję.
- Encje JPA nie mogą być rekordami (potrzebny konstruktor bezargumentowy i mutowalność). Rekordy stosuj jako DTO.
- Wstrzykuj przez konstruktor, nie przez `@Autowired` na polu.

**Projekt 8: REST API systemu wypożyczalni/biblioteki (lub zgłoszeń, czyli issue trackera)**
- Encje: użytkownicy, książki/zasoby, wypożyczenia z relacjami 1:N i N:M.
- CRUD + wyszukiwanie z paginacją i sortowaniem (`Pageable`), walidacja, globalna obsługa błędów (`ProblemDetail`).
- PostgreSQL + Flyway, profile `dev`/`test`/`prod`.
- Dokumentacja OpenAPI, Actuator.
- Testy: `@WebMvcTest` dla kontrolerów, `@DataJpaTest` + Testcontainers dla repozytoriów, test end-to-end `@SpringBootTest`.
- Uruchomienie przez Docker Compose. Spring Boot sam wykrywa `compose.yaml`.

---

## Etap 9: Backend produkcyjny (2 tygodnie)

- **Bezpieczeństwo:** Spring Security z JWT / OAuth2 Resource Server (np. Keycloak jako odpowiednik IdentityServera / Entra ID).
- **Komunikacja asynchroniczna:** Spring for Apache Kafka lub RabbitMQ (odpowiednik MassTransit/NServiceBus to ewentualnie Spring Cloud Stream).
- **Cache:** Spring Cache + Redis (`@Cacheable`).
- **Obserwowalność:** Micrometer + OpenTelemetry, Prometheus + Grafana (odpowiednik `System.Diagnostics.Metrics` / `ActivitySource`).
- **Odporność:** Resilience4j (odpowiednik Polly).
- **Klienci HTTP:** `RestClient` / deklaratywne `@HttpExchange` (jak Refit).
- **Wdrożenie:** obraz Dockera przez `bootBuildImage`/Jib, GraalVM native image (porównaj czas startu z JVM), virtual threads w Spring Boot (`spring.threads.virtual.enabled=true`).

**Projekt 9 (finałowy): Mini e-commerce w architekturze mikroserwisów**
- Serwisy: `catalog`, `orders`, `payments` (symulowany), `notifications`.
- Komunikacja: REST synchronicznie (catalog ↔ orders) + Kafka (zdarzenia `OrderPlaced`, `PaymentCompleted`), wzorzec Saga lub Outbox.
- Keycloak + JWT, API Gateway (Spring Cloud Gateway), Resilience4j na wywołaniach między serwisami.
- Tracing rozproszony (OpenTelemetry → Jaeger/Tempo), metryki w Grafanie.
- Docker Compose dla całości, CI z testami integracyjnymi na Testcontainers.
- Jeden serwis skompilowany jako native image.

---

## Etap 10 (opcjonalny): Poszerzenie horyzontów

| Temat | Kiedy warto | Odpowiednik .NET |
|---|---|---|
| **JavaFX** | Jeśli chcesz pisać desktop | WPF (FXML ≈ XAML, properties/bindings podobne do MVVM) |
| **Quarkus / Micronaut** | Szybki start, natywne obrazy, chmura | ASP.NET Core Minimal APIs + Native AOT |
| **Spring WebFlux / Reactor** | Systemy reaktywne (rzadziej od czasu virtual threads) | Rx.NET / `IAsyncEnumerable` |
| **Kotlin** | Wiele firm JVM go używa; składniowo bliższy C# (properties, null-safety, extension functions, coroutines ≈ async/await) | C# |
| **jOOQ** | Typowane SQL zamiast ORM | Dapper + LINQ to SQL |

**Projekt 10 (desktop, opcjonalny):** klient JavaFX do API z projektu 8 z architekturą MVVM, czyli to samo, co robiłeś w WPF, tylko w innych narzędziach.

---

## Ściągawka: najczęstsze nawyki z C#, których trzeba się oduczyć

1. `str1 == str2` zastąp przez `str1.equals(str2)` (lub `Objects.equals(a, b)` przy możliwym `null`).
2. Metody i pola piszesz w `camelCase`, `PascalCase` zostaje tylko dla typów.
3. Zamiast properties używasz getterów/setterów albo rekordów (`person.name()`).
4. `async/await` nie istnieje, więc stosujesz virtual threads + kod blokujący.
5. `using` zastępuje try-with-resources.
6. LINQ zastępuje Stream API, które jest jednorazowe, a `toList()` zwraca niemutowalną listę.
7. Rozwiązuj zdarzenia (events) wzorcem Listener.
8. Zamiast extension methods piszesz statyczne metody pomocnicze.
9. Pamiętaj o checked exceptions: deklaruj je albo opakowuj.
10. Generyki są wymazywane, więc gdy potrzebujesz typu w runtime, przekazujesz `Class<T>`.
11. Brak modyfikatora dostępu oznacza package-private, a nie `private` jak w C#.
12. Pojawia się osobny `null`-check dla typów opakowujących, a `Integer` to nie `int?` w sensie semantyki porównań.

---

## Polecane materiały

- **Książki:** *Effective Java* (J. Bloch, wyd. 3) to absolutny must-read; *Modern Java in Action* (Streams, lambdy, async); *Java Concurrency in Practice* (klasyka, wciąż aktualna w podstawach); *Spring Boot: Up and Running* / *Spring in Action*.
- **Online:** [dev.java](https://dev.java/learn/) (oficjalne tutoriale Oracle), [Baeldung](https://www.baeldung.com) (praktyczne artykuły o wszystkim w Javie/Springu), dokumentacja [Spring Guides](https://spring.io/guides), JEP-y na [openjdk.org](https://openjdk.org/jeps/0) (w nich opisano każdą nowość językową).
- **Ćwiczenia:** [Exercism – ścieżka Java](https://exercism.org/tracks/java) dla etapów 1–3.

---

## Śledzenie postępów

| Etap | Temat | Projekt | Status |
|---|---|---|---|
| 0 | Ekosystem | Hello, toolchain | ☐ |
| 1 | Składnia i typy | Todo CLI | ☐ |
| 2 | Generyki i kolekcje | LRU cache + rozbudowa Todo | ☐ |
| 3 | Lambdy i Streams | Analizator logów/transakcji | ☐ |
| 4 | Testy i build | Multi-module + CI | ☐ |
| 5 | I/O, HTTP, JSON | Klient API z cache | ☐ |
| 6 | Współbieżność | Web crawler | ☐ |
| 7 | JVM, refleksja | Mini DI + test runner | ☐ |
| 8 | Spring Boot | REST API biblioteki | ☐ |
| 9 | Produkcja | Mikroserwisy e-commerce | ☐ |
| 10 | Opcjonalnie | Klient JavaFX | ☐ |
