package com.kaesik.run.presentation.run_active

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class RunActiveViewModel: ViewModel() {
    var state by mutableStateOf(RunActiveState())
        private set

    private val eventChannel = Channel<RunActiveEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(false)

    fun onAction(action: RunActiveAction) {
        when (action) {
            RunActiveAction.OnFinishRunClick -> {
            }
            RunActiveAction.OnResumeRunClick -> {
            }
            RunActiveAction.OnToggleRunClick -> {
            }
            is RunActiveAction.SubmitLocationPermissionInfo -> {
                _hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(
                    showLocationRationale = action.showLocationRationale
                )
            }
            is RunActiveAction.SubmitNotificationPermissionInfo -> {
                state = state.copy(
                    showNotificationRationale = action.showNotificationRationale
                )
            }
            else -> Unit
        }
    }
}
