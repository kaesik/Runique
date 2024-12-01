package com.kaesik.analytics.presentation

import com.kaesik.analytics.domain.AnalyticsValues
import com.kaesik.core.presentation.ui.toFormatted
import com.kaesik.core.presentation.ui.toFormattedKm
import com.kaesik.core.presentation.ui.toFormattedKmh
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun Duration.toFormattedTotalTime(): String {
    val days = toLong(DurationUnit.DAYS)
    val hours = toLong(DurationUnit.HOURS) % 24
    val minutes = toLong(DurationUnit.MINUTES) % 60

    return if (days > 0) {
        "${days}d ${hours}h ${minutes}m"
    } else {
        "${hours}h ${minutes}m"
    }
}

fun AnalyticsValues.toAnalyticsDashboardState(): AnalyticsDashboardState {
    return AnalyticsDashboardState(
        totalDistanceRun = (totalDistanceRun / 1000.0).toFormattedKm(),
        totalTimeRun = totalTimeRun.toFormattedTotalTime(),
        fastestEverRun = fastestEverRun.toFormattedKmh(),
        avgDistance = avgDistancePerRun.toFormattedKm(),
        avgPace = avgPacePerRun.seconds.toFormatted()
    )
}
