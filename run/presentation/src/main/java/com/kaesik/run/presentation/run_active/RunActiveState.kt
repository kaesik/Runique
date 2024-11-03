package com.kaesik.run.presentation.run_active

import com.kaesik.core.domain.location.RuniqueLocation
import com.kaesik.run.domain.RunData
import kotlin.time.Duration

data class RunActiveState(
    val elapsedTime: Duration = Duration.ZERO,
    val runData: RunData = RunData(),
    val shouldTrack: Boolean = false,
    val hasStartedRunning: Boolean = false,
    val currentRuniqueLocation: RuniqueLocation? = null,
    val isRunningFinished: Boolean = false,
    val isSavingRun: Boolean = false,
    val showLocationRationale: Boolean = false,
    val showNotificationRationale: Boolean = false,
) {
}
