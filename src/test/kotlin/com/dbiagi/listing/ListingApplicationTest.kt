package com.dbiagi.listing

import io.kotest.core.spec.style.FunSpec
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.ComposeContainer
import java.io.File

@SpringBootTest(classes = [ListingApplication::class], webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
abstract class ListingApplicationTest(
    body: FunSpec.() -> Unit
) : FunSpec(body) {
    companion object {
        private val logger = KotlinLogging.logger {}

        @Suppress("UNUSED")
        private val DOCKER_COMPOSE = ComposeContainer(File("docker-compose.yml")).apply {
            withExposedService("rabbitmq-1", 5672)
            withExposedService("postgres-1", 5432)
            start()
            logger.info("Test containers started")
        }
    }
}

