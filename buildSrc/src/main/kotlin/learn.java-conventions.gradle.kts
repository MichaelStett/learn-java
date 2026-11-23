// Wspólna konfiguracja każdego modułu Java (odpowiednik Directory.Build.props).
// Użycie w module: plugins { id("learn.java-conventions") }

plugins {
    java
}

val libs = the<VersionCatalogsExtension>().named("libs")

java {
    toolchain {
        // Gradle sam pobierze JDK 25, jeśli nie ma go lokalnie (foojay resolver w settings.gradle.kts).
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    testImplementation(platform(libs.findLibrary("junit-bom").get()))
    testImplementation(libs.findLibrary("junit-jupiter").get())
    testImplementation(libs.findLibrary("assertj-core").get())
    testRuntimeOnly(libs.findLibrary("junit-platform-launcher").get())
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    // -parameters zachowuje nazwy parametrów w bytecode (przydatne dla refleksji i Springa).
    options.compilerArgs.addAll(listOf("-Xlint:all", "-parameters"))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// TODO (etap 4): dodaj tu pluginy `jacoco` i `checkstyle` oraz regułę minimalnego pokrycia.
