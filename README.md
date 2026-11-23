# learn-java

Repozytorium do nauki Javy według [PLAN.md](PLAN.md).

## Wymagania wstępne
1. **JDK 25** (do uruchomienia Gradle i IDE):
   ```
   winget install EclipseAdoptium.Temurin.25.JDK
   ```
   Następnie otwórz nowy terminal i sprawdź: `java -version`.
2. **Gradle** (tylko raz, żeby wygenerować wrapper): pobierz z https://gradle.org/install/ lub `scoop install gradle`, potem w katalogu repozytorium:
   ```
   gradle wrapper
   ```
   Od tej chwili używaj wyłącznie `./gradlew` (`gradlew.bat` w cmd/PowerShell). Wrapper commituj do repo,
   bo pełni rolę `global.json`, czyli przypina wersję narzędzia.
3. **IntelliJ IDEA**: *File → Open* na katalogu repozytorium, a projekt zaimportuje się jako build Gradle.
4. **Docker Desktop**: potrzebny od etapu 8 (PostgreSQL, Testcontainers).

## Struktura
```
learn-java/
├── PLAN.md                      # plan nauki
├── settings.gradle.kts          # lista modułów (≈ .sln)
├── build.gradle.kts             # pusty root build
├── gradle.properties
├── gradle/libs.versions.toml    # wersje zależności (≈ Directory.Packages.props)
├── buildSrc/                    # wspólna konfiguracja modułów (≈ Directory.Build.props)
│   └── src/main/kotlin/
│       ├── learn.java-conventions.gradle.kts   # JDK 25, JUnit 5, AssertJ, kodowanie, lint
│       └── learn.java-application.gradle.kts   # + plugin `application` (gradlew run)
└── projects/
    ├── 00-toolchain/            # ćwiczenie poza buildem (Gradle vs Maven od zera)
    ├── 01-todo-cli/             # :todo-cli       (aplikacja)
    ├── 02-collections/          # :collections    (biblioteka)
    ├── 03-log-analyzer/         # :log-analyzer   (aplikacja)
    ├── 04-common/               # :common         (biblioteka + Mockito)
    ├── 05-api-client/           # :api-client     (aplikacja, Jackson, SLF4J, WireMock)
    ├── 06-crawler/              # :crawler        (aplikacja, --enable-preview)
    ├── 07-mini-di/              # :mini-di        (biblioteka + JMH)
    ├── 08-library-api/          # :library-api    (Spring Boot, PostgreSQL, Flyway, Testcontainers)
    ├── 09-ecommerce/            # mikroserwisy, dołączane do builda w etapie 9
    └── 10-javafx-client/        # opcjonalny, dołączany do builda w etapie 10
```
Każdy projekt ma własne `README.md` z wymaganiami i kryteriami ukończenia.

## Najczęstsze komendy
| Gradle | Odpowiednik dotnet |
|---|---|
| `./gradlew build` | `dotnet build` + `dotnet test` |
| `./gradlew :todo-cli:run --args="list"` | `dotnet run --project ... -- list` |
| `./gradlew :todo-cli:test` | `dotnet test ...` |
| `./gradlew :todo-cli:test --tests "*TodoAppTest*"` | `dotnet test --filter` |
| `./gradlew :library-api:bootRun` | `dotnet run` (ASP.NET Core) |
| `./gradlew clean` | `dotnet clean` |
| `./gradlew dependencies` | `dotnet list package --include-transitive` |
| `./gradlew projects` | `dotnet sln list` |

## Dodawanie nowego modułu
1. Utwórz katalog `projects/NN-nazwa/` z `build.gradle.kts` (`plugins { id("learn.java-application") }` lub `id("learn.java-conventions")` + `` `java-library` ``).
2. Dopisz `"nazwa" to "projects/NN-nazwa"` do mapy `modules` w `settings.gradle.kts`.
3. Zależność od innego modułu: `implementation(project(":collections"))` (≈ `<ProjectReference>`).
