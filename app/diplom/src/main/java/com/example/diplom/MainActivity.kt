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
import com.example.diplom.routes.Routes
import com.example.diplom.scan.Scan
import com.example.diplom.ui.theme.DiplomaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiplomaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain()
                }
            }
        }
    }
}

@Composable
fun ScreenMain() {
//    val isUserAuthorized = false
    val navController = rememberNavController()

    registerAuthGraph(navController = navController)

//    registerAppGraph(navController = navController)
//    if (isUserAuthorized) {
//        registerAppGraph(navController = navController)
//    } else {
//        registerAuthGraph(navController = navController)
//    }
}

@Composable
fun registerAppGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Menu.route) {

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

@Composable
fun registerAuthGraph(navController: NavHostController) {
    var authViewModel = AuthViewModel()

    NavHost(navController = navController, startDestination = Routes.Auth.route) {

        composable(Routes.Auth.route) {
            Auth(navController = navController, viewModel = authViewModel)
        }

        composable(Routes.Menu.route) {
            Menu(navController = navController)
        }
    }
}
