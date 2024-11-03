package com.kaesik.run.domain

import com.kaesik.core.domain.location.RuniqueLocation
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val runiqueLocations: List<List<RuniqueLocation>> = emptyList()
)
