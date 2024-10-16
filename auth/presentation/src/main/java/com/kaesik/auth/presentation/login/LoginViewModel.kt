package com.kaesik.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kaesik.auth.domain.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private var eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    val authViewModelModule = module {
        viewModelOf(::RegisterViewModel)
        viewModelOf(::LoginViewModel)
    }

    fun onAction(action: LoginAction) {

    }
}
