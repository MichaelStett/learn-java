package dev.learnjava.apiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klient CLI publicznego API z cache (etap 5: I/O, java.time, HttpClient, Jackson).
 */
public final class ApiClientApp {

    private static final Logger log = LoggerFactory.getLogger(ApiClientApp.class);

    private ApiClientApp() {
    }

    public static void main(String[] args) {
        // TODO: java.net.http.HttpClient -> JSON -> record przez Jackson ObjectMapper, cache na dysku z TTL.
        log.info("API client wystartował");
    }
}
