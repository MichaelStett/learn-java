// Moduł uruchamialny z linii poleceń (odpowiednik <OutputType>Exe</OutputType>).
// Uruchamianie: ./gradlew :todo-cli:run --args="add 'Kupić mleko'"

plugins {
    id("learn.java-conventions")
    application
}

tasks.named<JavaExec>("run") {
    // Pozwala czytać z klawiatury (System.in) przy `gradlew run`.
    standardInput = System.`in`
}
