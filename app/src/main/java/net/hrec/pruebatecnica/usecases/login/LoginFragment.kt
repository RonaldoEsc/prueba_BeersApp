package net.hrec.pruebatecnica.usecases.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import net.hrec.pruebatecnica.databinding.FragmentLoginBinding
import net.hrec.pruebatecnica.usecases.common.interfaces.NavEventListener
import net.hrec.pruebatecnica.usecases.home.HomeActivity
import net.hrec.pruebatecnica.util.Constants.Companion.PREF_NAME
import net.hrec.pruebatecnica.util.Constants.Companion.PREF_PASS
import net.hrec.pruebatecnica.util.Constants.Companion.PREF_USER

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var usernameEditText: AppCompatEditText
    private lateinit var passwordEditText: TextInputEditText
    private val isGoingToRegister = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        usernameEditText = binding.etUserEmail
        passwordEditText = binding.etUserPass
        val loginButton = binding.login
        val loadingProgressBar = binding.loading
        val registerButton = binding.btnRegister

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                if (isGoingToRegister) {
                    if (registerButton != null) {
                        registerButton.isEnabled = loginFormState.isDataValid
                    }
                }
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.registerResult.observe(viewLifecycleOwner,
        Observer { registerResult ->
            registerResult ?: return@Observer
            loadingProgressBar.visibility = View.GONE
            registerResult.error?.let {
                showLoginFailed(it)
            }
            registerResult.success?.let {
                userRegistered()
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser()
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    Base64.encodeToString(passwordEditText.text.toString().toByteArray(), Base64.DEFAULT)
                )
            }
            false
        }
        if (isGoingToRegister) {
            registerButton?.visibility = View.VISIBLE
            registerButton?.setOnClickListener {
                loadingProgressBar.visibility = View.VISIBLE
                loginViewModel.register(
                    usernameEditText.text.toString(),
                    Base64.encodeToString(passwordEditText.text.toString().toByteArray(), Base64.DEFAULT)
                )
            }
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                usernameEditText.text.toString(),
                Base64.encodeToString(passwordEditText.text.toString().toByteArray(), Base64.DEFAULT)
            )
        }
    }

    private fun userRegistered() {
        val appContext = context?.applicationContext ?: return
        val message = "usuario registrado con exito"
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT)
    }

    private fun updateUiWithUser() {
        //val welcome = getString(R.string.welcome) + model.displayName
        val navEvent: NavEventListener = activity as HomeActivity
        val event = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        navEvent.onNavigateChangeEvent(event)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = activity ?: return
        Handler(Looper.getMainLooper()).post {
                AlertDialog.Builder(appContext)
                    .setMessage("Usuario y contraseña incorrectos intente de nuevo.")
                    .setPositiveButton("claro") { dialog, _->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                //Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
            }
    }
}