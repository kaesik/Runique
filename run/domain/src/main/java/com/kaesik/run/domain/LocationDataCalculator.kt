package com.kaesik.run.domain

import com.kaesik.core.domain.location.RuniqueLocation
import kotlin.math.roundToInt
import kotlin.time.DurationUnit

object LocationDataCalculator {
    fun getTotalDistanceMeter(locations: List<List<RuniqueLocation>>): Int {
        return locations
            .sumOf { timestampsPerLine ->
                timestampsPerLine.zipWithNext { location1, location2 ->
                    println(location1.distanceTo(location2))
                    location1.distanceTo(location2)
                }.sum().roundToInt()
            }
    }

    fun getMaxSpeedKmh(locations: List<List<RuniqueLocation>>): Double {
        return locations.maxOf { locationSet ->
            locationSet.zipWithNext { location1, location2 ->
                val distance = location1.distanceTo(location2)
                val hoursDifference = (location2.durationTimestamp - location1.durationTimestamp)
                    .toDouble(DurationUnit.HOURS)

                if (hoursDifference == 0.0) {
                    0.0
                } else {
                    (distance / 1000.0) / hoursDifference
                }
            }.maxOrNull() ?: 0.0
        }
    }

    fun getTotalElevationMeters(locations: List<List<RuniqueLocation>>): Int {
        return locations.sumOf { locationSet ->
            locationSet.zipWithNext { location1, location2 ->
                val altitude1 = location1.alt
                val altitude2 = location2.alt
                (altitude2 - altitude1).coerceAtLeast(0.0)
            }.sum().roundToInt()
        }
    }
}
