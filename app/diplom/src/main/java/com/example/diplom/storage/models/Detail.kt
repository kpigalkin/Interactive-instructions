package com.example.diplom.storage.models

import com.example.diplom.storage.entities.DetailsEntity

data class Detail(
    val id: Long,
    val title: String,
    val count: Int
)

fun Detail.toEntity(): DetailsEntity {
    return DetailsEntity(
        id = 0,
        title = this.title,
        count = this.count
    )
}

fun List<Detail>.toEntities(): List<DetailsEntity> {
    return this.map {
        DetailsEntity(
            id = 0,
            title = it.title,
            count = it.count
        )
    }
}