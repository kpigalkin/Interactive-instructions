package com.example.diplom.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.diplom.storage.models.Log
import com.example.diplom.util.Converters
import java.util.Date

@Entity(
    tableName = "logs"
)
data class LogsEntity(
    @TypeConverters(Converters::class)
    @PrimaryKey(autoGenerate = true) val date: Date,
    @ColumnInfo(name = "link") val link: String,
)

fun LogsEntity.toModel() = Log(
    date = this.date,
    link = this.link
)

fun List<LogsEntity>.toModels() = listOf(
    this.map {
        Log(
            date = it.date,
            link = it.link
        )
    }
)