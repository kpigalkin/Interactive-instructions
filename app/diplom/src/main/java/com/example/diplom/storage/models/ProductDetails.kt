package com.example.diplom.storage.models

import com.example.diplom.storage.entities.ProductDetailsEntity

data class ProductDetails(
    val id: Long = 0,
    val idProduct: Long,
    val idDetail: Long,
    val count: Int
)

fun ProductDetails.toEntity(): ProductDetailsEntity {
    return ProductDetailsEntity(
        id = this.id,
        idProduct = this.idProduct,
        idDetail = this.idDetail,
        count = this.count
    )
}

fun List<ProductDetails>.toEntities(): List<ProductDetailsEntity> {
    return this.map {
        ProductDetailsEntity(
            id = it.id,
            idProduct = it.idProduct,
            idDetail = it.idDetail,
            count = it.count
        )
    }
}