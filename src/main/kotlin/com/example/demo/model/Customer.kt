package com.example.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("customer")
data class Customer(
    @Id @Column("customer_id") var customerId: String? = null,
    @Column("name") val name: String
) : Persistable<String> {
    override fun getId() = UUID.randomUUID().toString()

    override fun isNew(): Boolean {
        if (customerId == null) {
            customerId = UUID.randomUUID().toString()

            return true
        }

        return false
    }
}