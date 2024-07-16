package com.gaebaljip.exceed.common;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;

@ActiveProfiles("test")
public abstract class ContainerTest {
    static final String MARIA_DB_IMAGE = "mariadb:10.6";
    static final MariaDBContainer Maria_DB_CONTAINER;
    static final String REDIS_IMAGE = "redis:6-alpine";
    static final GenericContainer REDIS_CONTAINER;

    static {
        Maria_DB_CONTAINER = new MariaDBContainer(MARIA_DB_IMAGE);
        Maria_DB_CONTAINER.start();
        REDIS_CONTAINER =
                new GenericContainer<>(REDIS_IMAGE).withExposedPorts(6379).withReuse(true);
        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> "" + REDIS_CONTAINER.getMappedPort(6379));
    }
}
