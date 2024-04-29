package com.example.diplom.navigation

sealed class Routes(val route: String) {
    object Logs: Routes("logs")
    object Auth: Routes("auth")
    object Menu: Routes("menu")
    object Scan: Routes("scan")
    object Instructions: Routes("instructions")
}