package com.example.diplom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diplom.auth.Auth
import com.example.diplom.auth.AuthViewModel
import com.example.diplom.instructions.Instructions
import com.example.diplom.logs.Logs
import com.example.diplom.menu.Menu
import com.example.diplom.navigation.Routes
import com.example.diplom.scan.Scan
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.entities.DetailsEntity
import com.example.diplom.storage.entities.ProductEntity
import com.example.diplom.ui.theme.DiplomaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dependencies.init(applicationContext)

        setContent {
            DiplomaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain()
                }
            }
        }
        fillDatabase()
    }

    private fun fillDatabase() {
        val products = listOf<ProductEntity>(
            ProductEntity(link = "http://1", description = "product 1"),
            ProductEntity(link = "http://2", description = "product 2"),
            ProductEntity(link = "http://3", description = "product 3"),
            ProductEntity(link = "http://4", description = "product 4"),
            ProductEntity(link = "http://5", description = "product 5"),
            ProductEntity(link = "http://6", description = "product 6"),
            ProductEntity(link = "http://7", description = "product 7"),
            ProductEntity(link = "http://8", description = "product 8"),
            ProductEntity(link = "http://9", description = "product 9")
        )

        val details = listOf<DetailsEntity>(
            DetailsEntity(title = "Шкант", count = 8982),
            DetailsEntity(title = "Левая дверца", count = 1),
            DetailsEntity(title = "Правая дверца", count = 8),
            DetailsEntity(title = "Дверная петля", count = 120),
            DetailsEntity(title = "Крышка", count = 2),
            DetailsEntity(title = "Полка центральная", count = 52)
        )
//        lifecycleScope.launch {
//            products.forEach {
//                Dependencies.repository.insertProduct(it)
//            }
//
//            details.forEach {
//                Dependencies.repository.insertDetail(it)
//            }
//        }

//        val detailsInProducts = listOf<ProductDetails>(
//            ProductDetails(id = 0, idProduct = 1, idDetail = 1, count = 55),
//            ProductDetails(id = 0, idProduct = 1, idDetail = 2, count = 1),
//            ProductDetails(id = 0, idProduct = 1, idDetail = 3, count = 1),
//            ProductDetails(id = 0, idProduct = 2, idDetail = 1, count = 55),
//            ProductDetails(id = 0, idProduct = 2, idDetail = 4, count = 1),
//            ProductDetails(id = 0, idProduct = 2, idDetail = 5, count = 1),
//            ProductDetails(id = 0, idProduct = 3, idDetail = 1, count = 55),
//            ProductDetails(id = 0, idProduct = 3, idDetail = 3, count = 8),
//            ProductDetails(id = 0, idProduct = 3, idDetail = 5, count = 9)
//        )
//        lifecycleScope.launch {
//            detailsInProducts.forEach {
//                Dependencies.repository.insertProductDetails(it)
//            }
//        }
    }

}

@Composable
fun ScreenMain() {
    /*
    TODO: Допилить авторизацию
    val isUserAuthorized = false
    */
    val navController = rememberNavController()
    NavGraph(navController = navController)
}

@Composable
fun NavGraph(navController: NavHostController) {
    var authViewModel = AuthViewModel(navController)

    NavHost(
        navController = navController,
        startDestination = Routes.Auth.route
    ) {
        composable(Routes.Auth.route) {
            Auth(viewModel = authViewModel)
        }

        composable(Routes.Menu.route) {
            Menu(navController = navController)
        }

        composable(Routes.Instructions.route) {
            Instructions()
        }

        composable(Routes.Logs.route) {
            Logs()
        }

        composable(Routes.Scan.route) {
            Scan()
        }
    }
}

