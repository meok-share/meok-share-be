package com.example.mapsearch.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebIntefrationTest {

    static final GenericContainer MY_REDIS_CONTAINER;

    static {
        MY_REDIS_CONTAINER = new GenericContainer("redis:latest")
                .withExposedPorts(6379);
        MY_REDIS_CONTAINER.start();

        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @LocalServerPort
    int port;

    public URI uri(String path) {
        try {
            return new URI(format("http://localhost:%d%s", port, path));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
