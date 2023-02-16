package com;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class DatabaseTestResource implements QuarkusTestResourceLifecycleManager {
    private final JdbcDatabaseContainer<?> database = new PostgreSQLContainer<>("postgres:11.11-alpine");

    @Override
    public Map<String, String> start() {
        database.start();
        return new HashMap<>() {{
            put("quarkus.datasource.jdbc.url", database.getJdbcUrl());
            put("quarkus.datasource.username", database.getUsername());
            put("quarkus.datasource.password", database.getPassword());
        }};
    }

    @Override
    public void stop() {
        database.stop();
    }
}