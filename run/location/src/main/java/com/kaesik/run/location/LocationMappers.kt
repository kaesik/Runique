package com.kaesik.run.location

import android.location.Location
import com.kaesik.core.domain.location.RuniqueLocation


fun Location.toLocationWithAlt(): RuniqueLocation {
    return RuniqueLocation(
        lat = latitude,
        long = longitude,
        alt = altitude,
    )
}
