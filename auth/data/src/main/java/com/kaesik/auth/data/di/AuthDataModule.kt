package com.kaesik.auth.data.di

import com.kaesik.auth.data.AuthRepositoryImpl
import com.kaesik.auth.data.EmailPatternValidator
import com.kaesik.auth.domain.AuthRepository
import com.kaesik.auth.domain.PatternValidator
import com.kaesik.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}