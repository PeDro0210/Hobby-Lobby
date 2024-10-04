package com.pedro0210.hobbylobby.model

data class LoginState(
    // States for sending the view model
    var email: String = "",
    var password: String = "",

    // Just for the screen
    val isPasswordVisible: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    var buttonText: String = "Login"

)