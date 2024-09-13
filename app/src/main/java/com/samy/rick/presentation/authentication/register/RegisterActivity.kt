package com.samy.rick.presentation.authentication.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.samy.e_ramo.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseUser
import com.samy.e_ramo.databinding.ActivityRegisterBinding
import com.samy.rick.presentation.main.MainActivity
import com.samy.rick.utils.DataState
import com.samy.rick.utils.SharedPrefsUtils
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set up Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setup()
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    Log.d("mos samy", "sate $state")
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
                                Intent(this@RegisterActivity, MainActivity::class.java)
                            )
                            finish()
                        }

                        is DataState.Error -> {
                            visProgress(false)
                            val exception = state.msg
                            Toast.makeText(this@RegisterActivity, exception, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setup() {
        binding.textViewLogin.setOnClickListener {
            finish()
        }
    }

    private fun visProgress(s: Boolean) {

        if (s) {
            hideKeyboard()
            binding.progressbar.visibility = View.VISIBLE
            binding.register.visibility = View.INVISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
            binding.register.visibility = View.VISIBLE
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