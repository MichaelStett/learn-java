pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    // Pozwala Gradle automatycznie pobrać JDK 25 wskazane w toolchainie (java { toolchain { ... } }).
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "learn-java"

dependencyResolutionManagement {
    // Wszystkie repozytoria deklarujemy tutaj, a nie w modułach (odpowiednik nuget.config na poziomie solution).
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}

// Moduł Gradle -> katalog. Odpowiednik listy projektów w pliku .sln.
// Etapy 00, 09 i 10 nie są tu (jeszcze) dołączone, więc zobacz ich README.
val modules = mapOf(
    "todo-cli" to "projects/01-todo-cli",
    "collections" to "projects/02-collections",
    "log-analyzer" to "projects/03-log-analyzer",
    "common" to "projects/04-common",
    "api-client" to "projects/05-api-client",
    "crawler" to "projects/06-crawler",
    "mini-di" to "projects/07-mini-di",
    "library-api" to "projects/08-library-api",
)

modules.forEach { (name, dir) ->
    include(name)
    project(":$name").projectDir = file(dir)
}
