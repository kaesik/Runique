package com.kaesik.run.domain

import com.kaesik.core.domain.location.Location
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<Location>> = emptyList()
)
