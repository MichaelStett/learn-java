# 10: Klient desktopowy JavaFX (opcjonalny)

**Etap 10: poszerzenie horyzontów.** Szczegóły w [PLAN.md](../../PLAN.md).

Moduł **nie jest dołączony do builda**. Gdy zaczniesz:
1. Dodaj plugin `org.openjfx.javafxplugin` do `gradle/libs.versions.toml` i utwórz tu `build.gradle.kts`
   (`id("learn.java-application")` + `javafx { modules("javafx.controls", "javafx.fxml") }`).
2. Dopisz `"javafx-client" to "projects/10-javafx-client"` do `settings.gradle.kts`.

## Wymagania
- Klient REST API z etapu 8: lista książek, wyszukiwanie, wypożyczanie.
- MVVM: widoki w FXML (≈ XAML), ViewModel z `StringProperty`/`ObservableList` i bindingami (≈ `INotifyPropertyChanged`).
- Wywołania HTTP poza wątkiem UI (`Task` z `javafx.concurrent` lub virtual threads + `Platform.runLater` ≈ `Dispatcher.Invoke`).
