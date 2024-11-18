package com.kaesik.run.presentation.run_active

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaesik.core.domain.location.RuniqueLocation
import com.kaesik.core.domain.run.Run
import com.kaesik.core.domain.run.RunRepository
import com.kaesik.core.domain.util.Result
import com.kaesik.core.presentation.ui.asUiText
import com.kaesik.run.domain.LocationDataCalculator
import com.kaesik.run.domain.RunningTracker
import com.kaesik.run.presentation.run_active.service.RunActiveService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime

class RunActiveViewModel(
    private val runningTracker: RunningTracker,
    private val runRepository: RunRepository,
): ViewModel() {
    var state by mutableStateOf(RunActiveState(
        shouldTrack = RunActiveService.isServiceActive && runningTracker.isTracking.value,
        hasStartedRunning = RunActiveService.isServiceActive
    ))
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
                state = state.copy(
                    isRunningFinished = true,
                    isSavingRun = true
                )
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
            is RunActiveAction.OnRunProcessed -> {
                finishRun(action.mapPicturesBytes)
            }
            else -> Unit
        }
    }

    private fun finishRun(mapPicturesBytes: ByteArray) {
        val locations = state.runData.runiqueLocations
        if (locations.isEmpty() || locations.first().size <= 1) {
            state = state.copy(isSavingRun = false)
            return
        }

        viewModelScope.launch {
            val run = Run(
                id = null,
                duration = state.elapsedTime,
                dateTimeUtc = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC")),
                distanceMeters = state.runData.distanceMeters,
                location = state.currentRuniqueLocation ?: RuniqueLocation(0.0, 0.0),
                maxSpeedKmh = LocationDataCalculator.getMaxSpeedKmh(locations),
                totalElevationMeters = LocationDataCalculator.getTotalElevationMeters(locations),
                mapPictureUrl = null
            )

            runningTracker.finishRun()
            when (val result = runRepository.upsertRun(run, mapPicturesBytes)) {
                is Result.Error -> {
                    eventChannel.send(RunActiveEvent.Error(result.error.asUiText()))
                }
                is Result.Success -> {
                    eventChannel.send(RunActiveEvent.RunSaved)
                }
            }

            state = state.copy(isSavingRun = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!RunActiveService.isServiceActive) {
            runningTracker.stopObservingLocation()
        }
    }
}
