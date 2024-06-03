package com.example.diplom.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplom.storage.entities.DetailsEntity
import com.example.diplom.storage.entities.LogsEntity
import com.example.diplom.storage.entities.ProductDetailsEntity
import com.example.diplom.storage.entities.ProductEntity
import com.example.diplom.storage.entities.toModels
import com.example.diplom.storage.models.Detail
import com.example.diplom.storage.models.Log
import com.example.diplom.storage.models.Product
import com.example.diplom.storage.models.ProductDetails
import com.example.diplom.storage.models.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface DAO {

    @Insert(entity = ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductEntity)

    @Insert(entity = DetailsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertDetail(detail: DetailsEntity)

    @Insert(entity = ProductDetailsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertDetail(productDetails: ProductDetailsEntity)

    @Insert(entity = LogsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertLog(log: LogsEntity)

    @Query("SELECT * FROM details")
    fun getDetails(): List<DetailsEntity>

    @Query("SELECT * FROM product")
    fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM product_details")
    fun getProductDetails(): List<ProductDetailsEntity>

    @Query("SELECT * FROM logs")
    fun getLogs(): List<LogsEntity>
}

class StorageRepository(private val storage: DAO) {

    suspend fun insertLog(log: Log) {
        withContext(Dispatchers.IO) {
            storage.insertLog(log.toEntity())
        }
    }
    suspend fun insertProduct(product: Product) {
        withContext(Dispatchers.IO) {
            storage.insertProduct(product.toEntity())
        }
    }
    suspend fun insertDetail(detail: Detail) {
        withContext(Dispatchers.IO) {
            storage.insertDetail(detail.toEntity())
        }
    }
    suspend fun insertProductDetails(productDetail: ProductDetails) {
        withContext(Dispatchers.IO) {
            storage.insertDetail(productDetail.toEntity())
        }
    }

    suspend fun getDetailsList(): List<Detail> {
        return withContext(Dispatchers.IO) {
            return@withContext storage.getDetails().toModels()
        }
    }
    suspend fun getProductList(): List<Product> {
        return withContext(Dispatchers.IO) {
            return@withContext storage.getProducts().toModels()
        }
    }
    suspend fun getProductDetailsList(): List<ProductDetails> {
        return withContext(Dispatchers.IO) {
            return@withContext storage.getProductDetails().toModels()
        }
    }

    suspend fun getLogsList(): List<Log> {
        return withContext(Dispatchers.IO) {
            return@withContext storage.getLogs().toModels()
        }
    }
}