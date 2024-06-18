package com.dbiagi.listing

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(classes = [ListingApplication::class], webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [TestContainerContextInitializer::class])
@ActiveProfiles("test")
abstract class ListingApplicationTest(
    body: FunSpec.() -> Unit
) : FunSpec(body)
