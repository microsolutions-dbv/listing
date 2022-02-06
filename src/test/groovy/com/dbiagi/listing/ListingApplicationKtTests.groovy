package com.dbiagi.listing

import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles(value = ["test"])
@ContextConfiguration(loader = SpringBootContextLoader, classes = [ListingApplication])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListingApplicationKtTests {
}
