package com.samy.rick.presentation.authentication.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseUser
import com.samy.e_ramo.R
import com.samy.e_ramo.databinding.ActivityLoginBinding
import com.samy.rick.presentation.authentication.register.RegisterActivity
import com.samy.rick.presentation.main.MainActivity
import com.samy.rick.utils.DataState
import com.samy.rick.utils.SharedPrefsUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setup()
        observeViewModel()
    }

    private fun setup() {

        binding.registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

        }

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    Log.d("mos samy", "state: $state")
                    when (state) {
                        is DataState.Idle -> {
                            visProgress(false)
                        }
                        is DataState.Loading -> {
                            visProgress(true)
                        }

                        is DataState.Result<*> -> {
                            SharedPrefsUtils.saveLastEmail(
                                getApplication(),
                                (state.data as FirebaseUser)?.email ?: ""
                            )
                            startActivity(
                                Intent(this@LoginActivity, MainActivity::class.java)
                            )
                            finish()
                        }

                        is DataState.Error -> {
                            visProgress(false)
                            // Show error message
                            val errorMessage = state.msg
                            Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> { /* Do nothing */
                        }
                    }
                }
            }
        }
    }

    private fun visProgress(s: Boolean) {

        if (s) {
            hideKeyboard()
            binding.progressbar.visibility = View.VISIBLE
            binding.login.visibility = View.INVISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
            binding.login.visibility = View.VISIBLE
        }

    }
    fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}