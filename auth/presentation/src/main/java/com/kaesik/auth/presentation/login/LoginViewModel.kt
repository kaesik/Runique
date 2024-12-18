package com.kaesik.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.auth.domain.AuthRepository
import com.kaesik.auth.domain.UserDataValidator
import com.kaesik.auth.presentation.R
import com.kaesik.auth.presentation.textAsFlow
import com.kaesik.core.domain.util.DataError
import com.kaesik.core.domain.util.RuniqueResult
import com.kaesik.core.presentation.ui.UiText
import com.kaesik.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
): ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private var eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(state.email.textAsFlow(), state.password.textAsFlow()) {email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(
                    email = email.toString().trim()
                ) && password.isNotBlank()
            )
        }.launchIn(viewModelScope)
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy( isLoggingIn = true )
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString(),
            )
            state = state.copy( isLoggingIn = false )

            when (result) {
                is RuniqueResult.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(LoginEvent.Error(
                            UiText.StringResource(R.string.error_email_password_incorrect)
                        ))
                    } else (
                        eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    )
                }
                is RuniqueResult.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }
}
