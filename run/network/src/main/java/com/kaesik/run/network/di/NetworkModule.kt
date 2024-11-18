package com.kaesik.run.network.di

import com.kaesik.core.domain.run.RemoteRunDataSource
import com.kaesik.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}
