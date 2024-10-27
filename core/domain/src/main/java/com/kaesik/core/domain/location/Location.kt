package com.kaesik.core.domain.location

import kotlin.time.Duration

data class Location(
    val lat: Double,
    val long: Double,
    val alt: Double? = null,
    val durationTimestamp: Duration? = null,
)
