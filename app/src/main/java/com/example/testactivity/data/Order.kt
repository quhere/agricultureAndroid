package com.example.testactivity.data

import java.time.LocalDateTime

data class Order(
    val id: Long,
    val product: Product,
    val distributorId: Long,
    val quantity: Long?,
    val orderedDate: String?,
    val sentDate: String?,
    val receivedDate: String?,
    val status: String?,
    val warehouseId: Long?
)
