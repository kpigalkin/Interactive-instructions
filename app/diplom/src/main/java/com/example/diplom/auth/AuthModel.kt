package com.example.diplom.auth

interface AuthModelInterface {
    fun auth(userLogin: String, userPassword: String): Boolean
}

class AuthModel: AuthModelInterface {
    val stabLogin = "admin"
    val stabPassword = "root"

    override fun auth(userLogin: String, userPassword: String): Boolean {
        val login = "admin"
        val password = "admin"
        return userLogin == login && userPassword == password
    }
}