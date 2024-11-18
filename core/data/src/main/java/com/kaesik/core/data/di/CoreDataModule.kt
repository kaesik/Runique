package com.kaesik.core.data.di

import com.kaesik.core.data.auth.EncryptedSessionStorage
import com.kaesik.core.data.networking.HttpClientFactory
import com.kaesik.core.data.run.OfflineFirstRunRepository
import com.kaesik.core.domain.SessionStorage
import com.kaesik.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}
