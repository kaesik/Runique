package com.kaesik.auth.data

import com.kaesik.auth.domain.AuthRepository
import com.kaesik.core.data.networking.post
import com.kaesik.core.domain.util.DataError
import com.kaesik.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository {
    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            ),
        )
    }
}