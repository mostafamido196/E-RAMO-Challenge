package com.samy.rick.presentation.authentication.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.samy.rick.domain.usecase.RegisterUserUseCase
import com.samy.rick.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    private val _registerState = MutableStateFlow<DataState<FirebaseUser?>>(DataState.Idle)
    val registerState: MutableStateFlow<DataState<FirebaseUser?>>
        get() = _registerState

    // ui
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    fun register() {
        Log.d("mos samy", "on viewModel register")
        Log.d("mos samy", "name: ${name}, email: $email ,pass: $password, age: $age")
        val nameValue = name.value ?: ""
        val emailValue = email.value ?: ""
        val ageValue = age.value ?: "16"
        val userPasswordValue = password.value ?: ""
        val userConfirmPasswordValue = confirmPassword.value ?: ""

        if (nameValue.isBlank()) {
            _registerState.value = DataState.Error("Name cannot be empty")
            return
        }

        if (!isValidEmail(emailValue)) {
            _registerState.value = DataState.Error("Invalid email format")
            return
        }

        if (ageValue.toIntOrNull() == null || ageValue.toInt() < 16) {
            _registerState.value = DataState.Error("Age must be a valid number and at least 16")
            return
        }

        if (userPasswordValue.length < 6) {
            _registerState.value = DataState.Error("Password must be at least 6 characters long")
            return
        }
        if (userPasswordValue != userConfirmPasswordValue) {
            _registerState.value = DataState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            registerUserUseCase(nameValue, emailValue, userPasswordValue, ageValue.toInt()).collect {
                _registerState.value = it
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
