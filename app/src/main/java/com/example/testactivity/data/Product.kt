package com.example.testactivity.data

import com.google.gson.annotations.SerializedName
import java.io.Serial
import java.time.LocalDateTime

data class Product(
    val productId: Long,
    val productName: String?,
    val productBrand: String?,
    val productOrigin: String?,
    val productCertification: String?,
    val productWeight: String?,
    val productCommit: String?,
    val productPlanting: String?,
    val quantity: Long?,
    val characteristic: String?,
    val seed: String?,
    val cook: String?,
    val note: String?,
    val image: String?,
    val plantingDate: String?,
    val harvestDate: String?,
    val supplierId: Long?,
    val supplier: Supplier?
)
