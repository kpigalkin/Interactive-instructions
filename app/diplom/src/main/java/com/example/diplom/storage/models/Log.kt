package com.example.diplom.storage.models

import com.example.diplom.storage.entities.LogsEntity
import java.util.Date

data class Log(
    val date: Date,
    val link: String
)

fun Log.toEntity(): LogsEntity = LogsEntity(
    date = this.date,
    link = this.link
)

fun List<Log>.toEntities() = listOf(
    this.map {
        LogsEntity(
            date = it.date,
            link = it.link
        )
    }
)