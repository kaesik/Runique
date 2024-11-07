package com.kaesik.run.presentation.run_active.maps

import androidx.compose.ui.graphics.Color
import com.kaesik.core.domain.location.RuniqueLocation

data class PolylineUi(
    val location1: RuniqueLocation,
    val location2: RuniqueLocation,
    val color: Color,
)
