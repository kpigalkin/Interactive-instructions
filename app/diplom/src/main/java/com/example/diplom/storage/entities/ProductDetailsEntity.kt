package com.example.diplom.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.diplom.storage.models.ProductDetails

@Entity(
    tableName = "product_details",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_product"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DetailsEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_detail"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class ProductDetailsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "id_product") val idProduct: Long,
    @ColumnInfo(name = "id_detail") val idDetail: Long,
    @ColumnInfo(name = "count") val count: Int
)

fun ProductDetailsEntity.toModel(): ProductDetails {
    return ProductDetails(
        id = this.id,
        idProduct = this.idProduct,
        idDetail = this.idDetail,
        count = this.count
    )
}

fun List<ProductDetailsEntity>.toModels(): List<ProductDetails> {
    return this.map {
        ProductDetails(
            id = it.id,
            idProduct = it.idProduct,
            idDetail = it.idDetail,
            count = it.count
        )
    }
}