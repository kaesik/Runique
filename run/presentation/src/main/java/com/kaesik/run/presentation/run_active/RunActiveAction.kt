package com.kaesik.run.presentation.run_active

sealed interface RunActiveAction {
    data object OnToggleRunClick: RunActiveAction
    data object OnFinishRunClick: RunActiveAction
    data object OnResumeRunClick: RunActiveAction
    data object OnBackClick: RunActiveAction
    data class SubmitLocationPermissionInfo(
        val acceptedLocationPermission: Boolean,
        val showLocationRationale: Boolean,
    ): RunActiveAction

    data class SubmitNotificationPermissionInfo(
        val acceptedNotificationPermission: Boolean,
        val showNotificationRationale: Boolean,
    ): RunActiveAction
    data object DismissRationaleDialog: RunActiveAction
}
