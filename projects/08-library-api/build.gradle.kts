plugins {
    id("learn.java-conventions")
    alias(libs.plugins.spring.boot)
}

dependencies {
    // BOM Spring Boot zarządza wersjami wszystkich startów (jak metapakiet Microsoft.AspNetCore.App).
    implementation(platform(libs.spring.boot.bom))
    testImplementation(platform(libs.spring.boot.bom))
    developmentOnly(platform(libs.spring.boot.bom))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgresql)
    implementation(libs.springdoc.openapi)
    runtimeOnly(libs.postgresql)

    // Przy `bootRun` Spring sam uruchamia compose.yaml (PostgreSQL) i konfiguruje datasource.
    developmentOnly(libs.spring.boot.docker.compose)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.junit)
    testImplementation(libs.testcontainers.postgresql)
}
