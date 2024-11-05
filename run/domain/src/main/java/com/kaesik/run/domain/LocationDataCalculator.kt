package com.kaesik.run.domain

import com.kaesik.core.domain.location.RuniqueLocation
import kotlin.math.roundToInt

object LocationDataCalculator {
    fun getTotalDistanceMeter(locations: List<List<RuniqueLocation>>): Int {
        return locations
            .sumOf { timestampsPerLine ->
                timestampsPerLine.zipWithNext { location1, location2 ->
                    location1.distanceTo(location2)
                }.sum().roundToInt()
            }
    }
}
