package com.dbiagi.listing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories
class ListingApplication

fun main(args: Array<String>) {
    runApplication<ListingApplication>(*args)
}
