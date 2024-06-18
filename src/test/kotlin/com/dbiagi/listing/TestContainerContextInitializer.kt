package com.dbiagi.listing

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.ComposeContainer
import java.io.File

class TestContainerContextInitializer: ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val logger = mu.KotlinLogging.logger {}

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        ComposeContainer(File("docker-compose.yml")).apply {
            withExposedService("rabbitmq-1", 5672)
            withExposedService("postgres-1", 5432)
            start()
            logger.info("Docker compose container started")
        }
    }
}
