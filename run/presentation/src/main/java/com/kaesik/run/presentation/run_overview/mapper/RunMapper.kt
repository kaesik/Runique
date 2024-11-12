package com.kaesik.run.presentation.run_overview.mapper

import com.kaesik.core.domain.run.Run
import com.kaesik.core.presentation.ui.toFormatted
import com.kaesik.core.presentation.ui.toFormattedKm
import com.kaesik.core.presentation.ui.toFormattedKmh
import com.kaesik.core.presentation.ui.toFormattedMeters
import com.kaesik.core.presentation.ui.toFormattedPace
import com.kaesik.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.toFormatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUtr = mapPictureUrl,
    )
}
