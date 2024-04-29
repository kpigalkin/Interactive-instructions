package com.example.diplom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diplom.auth.Auth
import com.example.diplom.auth.AuthViewModel
import com.example.diplom.instructions.Instructions
import com.example.diplom.logs.Logs
import com.example.diplom.menu.Menu
import com.example.diplom.scan.Scan

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Auth.route
    ) {
        composable(Routes.Auth.route) {
            Auth(navController = navController, viewModel = authViewModel)
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