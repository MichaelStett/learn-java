plugins {
    id("learn.java-conventions")
    `java-library`
    alias(libs.plugins.jmh)
}

// Benchmarki JMH trzymaj w src/jmh/java. Uruchomienie: ./gradlew :mini-di:jmh
jmh {
    warmupIterations = 3
    iterations = 5
    fork = 1
}
