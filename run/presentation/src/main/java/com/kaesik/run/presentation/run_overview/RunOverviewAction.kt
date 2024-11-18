package com.kaesik.run.presentation.run_overview

import com.kaesik.run.presentation.run_overview.model.RunUi

sealed interface RunOverviewAction {
    data object OnStartClick: RunOverviewAction
    data object OnLogoutClick: RunOverviewAction
    data object OnAnalyticsClick: RunOverviewAction
    data class DeleteRuns(val runUi: RunUi): RunOverviewAction
}
