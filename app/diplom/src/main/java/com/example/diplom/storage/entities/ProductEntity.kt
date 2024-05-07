package com.example.diplom.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.diplom.storage.models.Product

@Entity(
    tableName = "product",
    indices = [Index("id")]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "description") val description: String
)

fun ProductEntity.toModel(): Product {
    return Product(
        id = this.id,
        link = this.link,
        description = this.description
    )
}

fun List<ProductEntity>.toModels(): List<Product> {
    return this.map {
        Product(
            id = it.id,
            link = it.link,
            description = it.description
        )
    }
}