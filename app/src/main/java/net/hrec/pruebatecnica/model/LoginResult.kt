package net.hrec.pruebatecnica.model


data class LoginResult(
    var success: LoggedInUserView? = null,
    var error: Int? = null
)

data class LoggedInUserView(
    val displayName: String
)

data class RegisterResult(
    var success: LoggedInUserView? = null,
    var error: Int? = null
)