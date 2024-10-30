package com.kaesik.run.domain

import com.kaesik.core.domain.location.Location

fun Location.toLocationAlt(): Location {
    return Location(
        lat = lat,
        long = long,
        alt = alt,
    )
}
