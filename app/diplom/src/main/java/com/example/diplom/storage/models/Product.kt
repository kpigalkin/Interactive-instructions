package com.example.diplom.storage.models

import com.example.diplom.storage.entities.ProductEntity

data class Product(
    val id: Long,
    val link: String,
    val description: String
)

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        link = this.link,
        description = this.description
    )
}

fun List<Product>.toEntities(): List<ProductEntity> {
    return this.map { product ->
        ProductEntity(
            id = product.id,
            link = product.link,
            description = product.description
        )
    }
}