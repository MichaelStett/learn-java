// Główny build jest celowo pusty. Wspólna konfiguracja modułów żyje w convention plugins
// w katalogu buildSrc (odpowiednik Directory.Build.props w .NET).

plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.jmh) apply false
}
