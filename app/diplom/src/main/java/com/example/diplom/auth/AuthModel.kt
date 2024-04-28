package com.example.diplom.auth

interface AuthModelInterface {
    fun auth(login: String, password: String): Boolean
}

class AuthModel: AuthModelInterface {
    val stabLogin = "admin"
    val stabPassword = "root"

    override fun auth(login: String, password: String): Boolean {
        return login == stabLogin && password == stabPassword
    }
}