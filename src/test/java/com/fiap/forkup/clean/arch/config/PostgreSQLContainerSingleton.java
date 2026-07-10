package com.fiap.forkup.clean.arch.config;

import org.testcontainers.containers.PostgreSQLContainer;

public final class PostgreSQLContainerSingleton {

    private static final PostgreSQLContainer<?> INSTANCE =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("forkup")
                    .withUsername("postgres")
                    .withPassword("postgres");

    static {
        INSTANCE.start();
    }

    private PostgreSQLContainerSingleton() {
    }

    public static PostgreSQLContainer<?> getInstance() {
        return INSTANCE;
    }

}
