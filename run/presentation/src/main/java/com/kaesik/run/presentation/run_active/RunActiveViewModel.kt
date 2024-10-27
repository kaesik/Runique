package com.kaesik.run.presentation.run_active

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class RunActiveViewModel: ViewModel() {
    var state by mutableStateOf(RunActiveState())
        private set

    private val eventChannel = Channel<RunActiveEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RunActiveAction) {

    }
}
