plugins {
    id("learn.java-application")
}

application {
    mainClass = "dev.learnjava.crawler.CrawlerApp"
    // StructuredTaskScope jest w Javie 25 nadal w wersji preview.
    applicationDefaultJvmArgs = listOf("--enable-preview")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test>().configureEach {
    jvmArgs("--enable-preview")
}
