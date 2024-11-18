package com.kaesik.run.presentation.run_active

import com.kaesik.core.presentation.ui.UiText

sealed interface RunActiveEvent {
    data class Error(val error: UiText): RunActiveAction, RunActiveEvent
    data object RunSaved: RunActiveAction, RunActiveEvent
}
