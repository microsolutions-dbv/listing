package com.dbiagi.listing

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringAutowireConstructorExtension
import io.kotest.extensions.spring.SpringExtension

object KotestExtension : AbstractProjectConfig() {
    override val parallelism: Int = 3
    override fun extensions(): List<Extension> = listOf(SpringExtension, SpringAutowireConstructorExtension)
}
