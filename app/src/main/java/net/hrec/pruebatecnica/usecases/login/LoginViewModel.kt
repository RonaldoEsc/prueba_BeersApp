package net.hrec.pruebatecnica.usecases.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth

import net.hrec.pruebatecnica.R
import net.hrec.pruebatecnica.model.LoggedInUserView
import net.hrec.pruebatecnica.model.LoginFormState
import net.hrec.pruebatecnica.model.LoginResult
import net.hrec.pruebatecnica.model.RegisterResult

class LoginViewModel : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun login(username: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = it.result.user.toString()))
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
    }

    fun register(username: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _registerResult.value =
                        RegisterResult(success = LoggedInUserView(displayName = it.result.user.toString()))
                } else {
                    _registerResult.value = RegisterResult(error = R.string.login_failed)
                }
            }
    }

    fun loginDataChanged(username: String, password: String) {
        if (isStringEmpty(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.empty_username)
        } else if (isStringEmpty(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.empty_username)
        } else if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            return false
        }
    }

    private fun isStringEmpty(text: String): Boolean {
        return text.isEmpty()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}