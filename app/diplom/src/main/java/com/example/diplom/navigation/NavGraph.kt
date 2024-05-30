package com.example.diplom.navigation

import MenuViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diplom.auth.Auth
import com.example.diplom.auth.AuthViewModel
import com.example.diplom.instructions.Instructions
import com.example.diplom.instructions.InstructionsViewModel
import com.example.diplom.logs.Logs
import com.example.diplom.menu.Menu
import com.example.diplom.scan.Scan
import com.example.diplom.scan.ScanViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Auth.route
    ) {
        composable(Routes.Auth.route) {
            Auth(viewModel = AuthViewModel(navController))
        }

        composable(Routes.Menu.route) {
            Menu(viewModel = MenuViewModel(navController))
        }

        composable(Routes.Instructions.route) {
            Instructions(viewModel = InstructionsViewModel())
        }

        composable(Routes.Logs.route) {
            Logs()
        }

        composable(Routes.Scan.route) {
            Scan(viewModel = ScanViewModel(navController))
        }
    }
}