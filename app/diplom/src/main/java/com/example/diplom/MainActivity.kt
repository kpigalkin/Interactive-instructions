@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.diplom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.diplom.navigation.NavGraph
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Detail
import com.example.diplom.storage.models.Product
import com.example.diplom.storage.models.ProductDetails
import com.example.diplom.ui.theme.DiplomaTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dependencies.init(applicationContext)

        setContent {
            FirebaseAuth.getInstance().signOut()
            DiplomaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }

//        fillDatabase()
    }

    private fun fillDatabase() {
        val products = listOf<Product>(
            Product(
                link = "https://ietm.igatec.com/PROMO/IETM_Display/Serve?page=object&oId=D7D98E1D-F729-422D-A878-1ABEF9557B60",
                description = "Замена фильтра насосной станции"
            ),
            Product(
                link = "https://ietm.igatec.com/PROMO/IETM_Display/Serve?page=object&oId=EC2DC40B-20B6-4431-876B-B985A1DC958C",
                description = "Снятие узлов опрокидывающего механизма с самосвала"
            ),
            Product(
                link = "https://ietm.igatec.com/PROMO/IETM_Display/Serve?page=object&oId=46362AAA-5C70-4D7F-AD8E-683C4FE1E568",
                description = "Разборка цилиндра опрокидывающего механизма"
            ),
            Product(
                link = "https://ietm.igatec.com/PROMO/IETM_Display/Serve?page=main",
                description = "Сборка комода №0001"
            ),
            Product(
                link = "https://ietm.igatec.com/PROMO/IETM_Display/Serve?page=main",
                description = "Сборка шкафа-купе №1231"
            ),
            Product(
                link = "https://ietm.igatec.com/PROMO/IETM_Display/Serve?page=main",
                description = "Сборка тумбы №0012"
            ),
            Product(
                link = "https://ietm.igatec.com/PROMO/IETM_Display/Serve?page=main",
                description = "Сборка тумбы №0981"
            ),
        )

        val details = listOf<Detail>(
            Detail(title = "Крышка", count = 10),
            Detail(title = "Боковая стенка левая", count = 10),
            Detail(title = "Полка боковая", count = 12),
            Detail(title = "Внутреняяя стенка левая", count = 11),
            Detail(title = "Боковая стенка правая", count = 16),
            Detail(title = "Внутреняя стенка правая", count = 6),
            Detail(title = "Полка центральная", count = 3),
            Detail(title = "Цоколь", count = 44),
            Detail(title = "Задняя стенка", count = 80),
            Detail(title = "Ответная планка дверной петли", count = 12),
            Detail(title = "Дверная петля", count = 2),
            Detail(title = "Дверца левая", count = 10),
            Detail(title = "Дверца правая", count = 10),
            Detail(title = "Стяжка мебельная шурупная 5x50", count = 9000),
            Detail(title = "Шуруп с полукруглой головкой 3.9x9.5", count = 5000),
        )

        val detailsInProducts = listOf<ProductDetails>(
            ProductDetails(idProduct = 4, idDetail = 1, count = 1),
            ProductDetails(idProduct = 4, idDetail = 2, count = 1),
            ProductDetails(idProduct = 4, idDetail = 3, count = 2),
            ProductDetails(idProduct = 4, idDetail = 4, count = 1),
            ProductDetails(idProduct = 4, idDetail = 5, count = 1),
            ProductDetails(idProduct = 4, idDetail = 6, count = 1),
            ProductDetails(idProduct = 4, idDetail = 7, count = 2),
            ProductDetails(idProduct = 4, idDetail = 8, count = 2),
            ProductDetails(idProduct = 4, idDetail = 9, count = 1),
            ProductDetails(idProduct = 4, idDetail = 10, count = 4),
            ProductDetails(idProduct = 4, idDetail = 11, count = 4),
            ProductDetails(idProduct = 4, idDetail = 12, count = 1),
            ProductDetails(idProduct = 4, idDetail = 13, count = 1),
            ProductDetails(idProduct = 4, idDetail = 14, count = 76),
            ProductDetails(idProduct = 4, idDetail = 15, count = 16)
        )


        lifecycleScope.launch {
            products.forEach {
                Dependencies.repository.insertProduct(it)
            }

            details.forEach {
                Dependencies.repository.insertDetail(it)
            }

            detailsInProducts.forEach {
                Dependencies.repository.insertProductDetails(it)
            }
        }
    }
}