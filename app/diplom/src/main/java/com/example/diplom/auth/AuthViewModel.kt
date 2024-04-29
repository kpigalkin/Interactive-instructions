package com.example.diplom.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.diplom.navigation.Routes

class AuthViewModel(navController: NavHostController): ViewModel() {
    private val _errorState = mutableStateOf<String?>(null)

    val errorState: State<String?> get () = _errorState

    private var authModel: AuthModelInterface = AuthModel()
    private lateinit var navController: NavHostController

    var login by mutableStateOf("")
    var password by mutableStateOf("")

    init {
        this.navController = navController
    }

    fun singInButtonClicked() {
        if (authModel.auth(login, password)) {
            return navController.navigate(Routes.Menu.route)
        } else {
            _errorState.value = "Wrong password"
        }
    }

    fun alertButtonClicked() {
        _errorState.value = null
    }
}


