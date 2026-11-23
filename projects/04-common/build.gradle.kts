plugins {
    id("learn.java-conventions")
    `java-library`
}

dependencies {
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)
}
