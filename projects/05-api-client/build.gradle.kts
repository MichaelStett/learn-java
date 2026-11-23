plugins {
    id("learn.java-application")
}

application {
    mainClass = "dev.learnjava.apiclient.ApiClientApp"
}

dependencies {
    implementation(project(":collections")) // LruCache z etapu 2
    implementation(platform(libs.jackson.bom))
    implementation(libs.jackson.databind)
    implementation(libs.jackson.jsr310)
    implementation(libs.slf4j.api)
    runtimeOnly(libs.logback.classic)

    testImplementation(libs.wiremock)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)
}
