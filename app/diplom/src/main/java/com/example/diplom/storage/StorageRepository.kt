package com.example.diplom.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diplom.storage.entities.DetailsEntity
import com.example.diplom.storage.entities.ProductDetailsEntity
import com.example.diplom.storage.entities.ProductEntity
import com.example.diplom.storage.entities.toModels
import com.example.diplom.storage.models.Detail
import com.example.diplom.storage.models.Product
import com.example.diplom.storage.models.ProductDetails
import com.example.diplom.storage.models.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface DAO {
    // PUT
    @Insert(entity = ProductEntity::class)
    fun insertProduct(product: ProductEntity)

    @Insert(entity = DetailsEntity::class)
    fun insertDetail(detail: DetailsEntity)

    @Insert(entity = ProductDetailsEntity::class)
    fun insertDetail(productDetails: ProductDetailsEntity)
    // GET
    @Query("SELECT * FROM details")
    fun getDetails(): List<DetailsEntity>

    @Query("SELECT * FROM product")
    fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM product_details")
    fun getProductDetails(): List<ProductDetailsEntity>
}

class StorageRepository(private val storage: DAO) {
    // PUT
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

    // GET
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
}

//  https://habr.com/ru/articles/713518/