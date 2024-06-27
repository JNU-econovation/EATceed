package com.gaebaljip.exceed.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.gaebaljip.exceed.config.QueryDslConfig;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=validate")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
public class SchemaValidationTest {

    private static MariaDBContainer<?> schemaValidationMariaDB;
    private static GenericContainer<?> redisContainer;

    private static String getAbsolutePath() {
        String relativePath = "resources/gaebaljip-develop-environment/mariadb-init/01_schema.sql";
        String currentDir = System.getProperty("user.dir");
        return Paths.get(currentDir, relativePath).normalize().toAbsolutePath().toString();
    }

    @BeforeAll
    public static void setUp() throws IOException, SQLException {
        String schemaFilePath = getAbsolutePath();

        if (!Files.exists(Paths.get(schemaFilePath))) {
            throw new IOException("해당 경로에 스키마 파일이 존재하지 않습니다. : " + schemaFilePath);
        }

        schemaValidationMariaDB = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.6"));
        schemaValidationMariaDB.start();

        redisContainer =
                new GenericContainer<>(DockerImageName.parse("redis:6-alpine"))
                        .withExposedPorts(6379);
        redisContainer.start();

        runInitScript(schemaValidationMariaDB, schemaFilePath);
    }

    private static void runInitScript(MariaDBContainer<?> container, String scriptPath)
            throws SQLException, IOException {
        String script = new String(Files.readAllBytes(Paths.get(scriptPath)));
        String[] commands = script.split(";");

        try (Connection connection = container.createConnection("");
                Statement statement = connection.createStatement()) {
            // Start from the second command
            for (int i = 1; i < commands.length; i++) {
                String command = commands[i].trim();
                if (!command.isEmpty()) {
                    statement.execute(command);
                }
            }
        }
    }

    @AfterAll
    public static void tearDown() {
        if (schemaValidationMariaDB != null) {
            schemaValidationMariaDB.stop();
        }
        if (redisContainer != null) {
            redisContainer.stop();
        }
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> schemaValidationMariaDB.getJdbcUrl());
        registry.add("spring.datasource.username", () -> schemaValidationMariaDB.getUsername());
        registry.add("spring.datasource.password", () -> schemaValidationMariaDB.getPassword());
        registry.add("spring.redis.host", () -> redisContainer.getHost());
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379).toString());
    }

    @Test
    public void testSchemaValidity() {}
}
