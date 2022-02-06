package com.dbiagi.listing.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

abstract class AbstractDocument {
    @Id
    private var _id: ObjectId? = null

    override fun hashCode(): Int = 31

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) {
            return true
        }

        if (this.javaClass != other.javaClass) {
            return false
        }

        other as AbstractDocument

        if (this.getId() == null || other.getId() == null) {
            return false
        }

        return this.getId() == other.getId()
    }

    fun getId(): ObjectId? = _id
}
