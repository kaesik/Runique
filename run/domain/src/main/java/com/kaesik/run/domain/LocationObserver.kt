package com.kaesik.run.domain

import com.kaesik.core.domain.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<Location>
}
