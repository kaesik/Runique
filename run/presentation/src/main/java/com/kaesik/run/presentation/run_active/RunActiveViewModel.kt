package com.kaesik.run.presentation.run_active

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaesik.run.domain.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class RunActiveViewModel(
    private val runningTracker: RunningTracker
): ViewModel() {
    var state by mutableStateOf(RunActiveState())
        private set

    private val eventChannel = Channel<RunActiveEvent>()
    val events = eventChannel.receiveAsFlow()

    private val shouldTrack = snapshotFlow { state.shouldTrack }
        .stateIn(viewModelScope, SharingStarted.Lazily, state.shouldTrack)
    private val hasLocationPermission = MutableStateFlow(false)
    private val isTracking = combine(
        shouldTrack,
        hasLocationPermission
    ) { shouldTrack, hasPermission ->
        shouldTrack && hasPermission
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        hasLocationPermission
            .onEach { hasPermission ->
                if (hasPermission) {
                    runningTracker.startObservingLocation()
                } else {
                    runningTracker.stopObservingLocation()
                }
            }
            .launchIn(viewModelScope)

        isTracking
            .onEach { isTracking ->
                runningTracker.setIsTracking(isTracking)
            }
            .launchIn(viewModelScope)

        runningTracker
            .currentLocation
            .onEach {
                state = state.copy(currentRuniqueLocation = it)
            }
            .launchIn(viewModelScope)

        runningTracker
            .runData
            .onEach {
                state = state.copy(runData = it)
            }
            .launchIn(viewModelScope)

        runningTracker
            .elapsedTime
            .onEach {
                state = state.copy(elapsedTime = it)
            }
            .launchIn(viewModelScope)

    }

    fun onAction(action: RunActiveAction) {
        when (action) {
            RunActiveAction.OnFinishRunClick -> {
            }
            RunActiveAction.OnResumeRunClick -> {
                state = state.copy(
                    shouldTrack = true
                )
            }
            RunActiveAction.OnBackClick -> {
                state = state.copy(
                    shouldTrack = false
                )
            }
            RunActiveAction.OnToggleRunClick -> {
                state = state.copy(
                    hasStartedRunning = true,
                    shouldTrack = !state.shouldTrack
                )
            }
            is RunActiveAction.SubmitLocationPermissionInfo -> {
                hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(
                    showLocationRationale = action.showLocationRationale
                )
            }
            is RunActiveAction.SubmitNotificationPermissionInfo -> {
                state = state.copy(
                    showNotificationRationale = action.showNotificationRationale
                )
            }
            is RunActiveAction.DismissRationaleDialog -> {
                state = state.copy(
                    showNotificationRationale = false,
                    showLocationRationale = false,
                )
            }
            else -> Unit
        }
    }
}
