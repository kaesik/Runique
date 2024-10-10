package com.kaesik.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.auth.domain.UserDataValidator
import com.kaesik.auth.presentation.textAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    init {
        state.email.textAsFlow()
            .onEach { email ->
                state = state.copy(
                    isEmailValid = userDataValidator.isValidEmail(email.toString())
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                state = state.copy(
                    passwordValidationState = userDataValidator.isValidPassword(password.toString())
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction){

    }
}