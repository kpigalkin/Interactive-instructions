package com.example.diplom.navigation

import MenuViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diplom.auth.Auth
import com.example.diplom.auth.AuthViewModel
import com.example.diplom.instructions.Instructions
import com.example.diplom.instructions.InstructionsViewModel
import com.example.diplom.logs.Logs
import com.example.diplom.logs.LogsViewModel
import com.example.diplom.menu.Menu
import com.example.diplom.scan.Scan
import com.example.diplom.scan.ScanViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
            Logs(LogsViewModel())
        }

        composable(Routes.Scan.route) {
            Scan(viewModel = ScanViewModel(navController))
        }
    }
}