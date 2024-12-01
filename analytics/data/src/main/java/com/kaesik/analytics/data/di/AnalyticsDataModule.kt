package com.kaesik.analytics.data.di

import com.kaesik.analytics.data.RoomAnalyticsRepository
import com.kaesik.analytics.domain.AnalyticsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsDataModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
}
