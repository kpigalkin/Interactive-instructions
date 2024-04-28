package com.example.diplom.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    private var authModel: AuthModelInterface = AuthModel()

    var login by mutableStateOf("")
    var password by mutableStateOf("")

    fun singInButtonClicked() {
        val result = authModel.auth(login, password)

        if (result == true) {

        } else {

        }
    }
}


