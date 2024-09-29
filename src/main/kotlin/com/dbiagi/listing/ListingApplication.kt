package com.dbiagi.listing

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(info = Info(title = "Listing API", version = "1.0"))
class ListingApplication

fun main(args: Array<String>) {
    runApplication<ListingApplication>(*args)
}
