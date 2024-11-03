package com.kaesik.run.domain

import com.kaesik.core.domain.location.RuniqueLocation
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<RuniqueLocation>
}
