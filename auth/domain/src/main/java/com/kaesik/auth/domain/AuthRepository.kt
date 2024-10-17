package com.kaesik.auth.domain

import com.kaesik.core.domain.util.DataError
import com.kaesik.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}
