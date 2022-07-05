package net.hrec.pruebatecnica.model

import net.hrec.pruebatecnica.usecases.login.LoggedInUserView

data class LoginResult(
    var success: LoggedInUserView? = null,
    var error: Int? = null
)