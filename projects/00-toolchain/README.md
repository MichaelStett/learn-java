# 00: Hello, toolchain

**Etap 0: ekosystem i narzędzia.** Szczegóły w [PLAN.md](../../PLAN.md).

Ten katalog **nie należy do głównego builda**. To ćwiczenie polega na samodzielnym wygenerowaniu projektów,
żeby zobaczyć, jak powstają od zera.

## Zadania
1. `jshell`: poeksperymentuj z `var`, `record`, `switch` i `List.of(...)`.
2. Pojedynczy plik bez builda: utwórz `Hello.java` i uruchom `java Hello.java`.
3. Gradle: w podkatalogu `gradle-hello` uruchom `gradle init` (application, Java, Kotlin DSL), dodaj zależność
   `org.apache.commons:commons-lang3`, zbuduj i uruchom (`gradlew run`, `gradlew build`).
4. Maven: w podkatalogu `maven-hello` wygeneruj projekt
   `mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false -DgroupId=dev.learnjava -DartifactId=maven-hello`,
   dodaj tę samą zależność, zbuduj fat-JAR (`maven-shade-plugin`) i uruchom `java -jar`.
5. Porównaj `pom.xml` z `build.gradle.kts` i z `.csproj`, a wnioski zapisz poniżej.

## Wnioski
_(uzupełnij)_
