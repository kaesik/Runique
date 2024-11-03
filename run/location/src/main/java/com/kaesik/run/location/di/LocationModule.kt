package com.kaesik.run.location.di

import com.kaesik.run.domain.LocationObserver
import com.kaesik.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}
