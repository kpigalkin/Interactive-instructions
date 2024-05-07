package com.example.diplom.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.diplom.storage.models.Detail

@Entity(
    tableName = "details",
    indices = [Index("id")]
)
data class DetailsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "count") val count: Int
)

fun DetailsEntity.toModel(): Detail {
    return Detail(
        id = this.id,
        title = this.title,
        count = this.count
    )
}

fun List<DetailsEntity>.toModels(): List<Detail> {
    return this.map {
        Detail(
            id = it.id,
            title = it.title,
            count = it.count
        )
    }
}