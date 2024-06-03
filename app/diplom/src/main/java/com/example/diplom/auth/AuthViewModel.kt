package com.example.diplom.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.diplom.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(navController: NavHostController): ViewModel() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    private val _errorState = mutableStateOf<String?>(null)
    val errorState: State<String?>
        get () = _errorState

    private lateinit var navController: NavHostController

    var login by mutableStateOf("")
    var password by mutableStateOf("")

    private val _hintText = mutableStateOf<String>("")
    val hintText: State<String> = _hintText

    private val _buttonText = mutableStateOf<String>("")
    val buttonText: State<String> = _buttonText

    init {
        this.navController = navController
        _buttonText.value = "Sign in"
        _hintText.value = "Don't have an account?"
    }

    fun buttonClicked() {
        if (login.isNotEmpty() && password.isNotEmpty()) {
            if (_buttonText.value == "Sign in") {
                firebaseAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        navController.navigate(Routes.Menu.route)
                    } else {
                        _errorState.value = "Wrong login or password"
                    }
                }
            } else {
                firebaseAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        navController.navigate(Routes.Menu.route)
                    } else {
                        _errorState.value = "Wrong login or password"
                    }
                }
            }
        } else {
            _errorState.value = "Empty forms"
        }
    }

    fun hintButtonClicked() {
        if (_buttonText.value == "Sign in") {
            _buttonText.value = "Sign up"
            _hintText.value = "Have an account?"
        } else {
            _buttonText.value = "Sign in"
            _hintText.value = "Don't have an account?"
        }
    }

    fun alertButtonClicked() {
        _errorState.value = null
    }
}


