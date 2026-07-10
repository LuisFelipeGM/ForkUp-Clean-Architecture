package com.fiap.forkup.clean.arch.controller;

import com.fiap.forkup.clean.arch.config.PostgreSQLContainerSingleton;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractControllerIT {

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {

        PostgreSQLContainer<?> postgres =
                PostgreSQLContainerSingleton.getInstance();

        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }

}
