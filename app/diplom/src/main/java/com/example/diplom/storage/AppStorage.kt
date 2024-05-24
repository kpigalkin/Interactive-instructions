package com.example.diplom.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diplom.storage.entities.DetailsEntity
import com.example.diplom.storage.entities.LogsEntity
import com.example.diplom.storage.entities.ProductDetailsEntity
import com.example.diplom.storage.entities.ProductEntity
import com.example.diplom.util.Converters

@Database(
    version = 1,
    entities = [
        ProductEntity::class,
        LogsEntity::class,
        DetailsEntity::class,
        ProductDetailsEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppStorage: RoomDatabase() {
    abstract fun getDAO(): DAO
}

object Dependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppStorage by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = AppStorage::class.java,
            name = "mydb4.db"
        ).build()
    }

    val repository: StorageRepository by lazy {
        StorageRepository(appDatabase.getDAO())
    }
}