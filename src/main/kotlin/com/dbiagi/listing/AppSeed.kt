package com.dbiagi.listing

import com.dbiagi.listing.config.Environment
import com.dbiagi.listing.domain.Listing
import com.dbiagi.listing.repository.ListingRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@Profile(Environment.DEV)
class AppSeed(
    val listingRepository: ListingRepository
) : ApplicationRunner {
    companion object {
        const val SEED_ARG = "seed"
        const val TRUNCATE_ARG = "truncate"
    }

    override fun run(args: ApplicationArguments) {
        if (!args.optionNames.contains(SEED_ARG)) {
            return
        }

        if (args.optionNames.contains(TRUNCATE_ARG)) {
            listingRepository.deleteAll().block()
        }

        createListings(20)
    }

    fun createListings(max: Int) {
        (0..max)
            .map { i: Int ->
                Listing(
                    title = "Listing Title $i",
                    description = "Listing description text of number $i",
                    featured = false,
                    price = BigDecimal.TEN + BigDecimal.valueOf(i.toLong()),
                    ownerId = "71f4707d-98ce-4508-a20e-c7e7463df81d",
                )
            }
            .let { listingRepository.saveAll(it) }
    }
}
