package com.samy.rick.presentation.authentication.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.samy.rick.domain.usecase.LoginUserUseCase
import com.samy.rick.utils.DataState
import com.samy.rick.utils.SharedPrefsUtils
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val application: Application
) : ViewModel() {

    private val _loginState = MutableStateFlow<DataState<FirebaseUser?>>(DataState.Idle)
    val loginState: MutableStateFlow<DataState<FirebaseUser?>>
        get() = _loginState

    // UI
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isPasswordVisible = MutableLiveData<Boolean>().apply { value = false }
    init {
        // Load last saved email
        loadLastEmail()
    }

    fun togglePasswordVisibility() {
        isPasswordVisible.value = !(isPasswordVisible.value ?: false)
    }
    private fun loadLastEmail() {
        val savedEmail = SharedPrefsUtils.getLastEmail(application)
        if (savedEmail != null && savedEmail.isNotEmpty()) {
            email.value = savedEmail!!
        }
    }
    fun login() {
        Log.d("mos samy", "on viewModel login")
        val emailValue = email.value ?: ""
        val passwordValue = password.value ?: ""

        if (emailValue.isBlank()) {
            _loginState.value = DataState.Error("Email cannot be empty")
            return
        }
        if (!isValidEmail(emailValue)) {
            _loginState.value = DataState.Error("Invalid email format")
            return
        }
        if (passwordValue.length < 6) {
            _loginState.value = DataState.Error("Password must be at least 6 characters")
            return
        }


        viewModelScope.launch {
            loginUserUseCase(emailValue, passwordValue).collect {
                _loginState.value = it
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
