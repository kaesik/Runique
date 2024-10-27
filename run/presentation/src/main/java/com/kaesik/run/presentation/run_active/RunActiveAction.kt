package com.kaesik.run.presentation.run_active

sealed interface RunActiveAction {
    data object OnToggleRunClick: RunActiveAction
    data object OnFinishRunClick: RunActiveAction
    data object OnResumeRunClick: RunActiveAction
    data object OnBackClick: RunActiveAction
}
