package com.kaesik.run.presentation.di

import com.kaesik.run.domain.RunningTracker
import com.kaesik.run.presentation.run_active.RunActiveViewModel
import com.kaesik.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::RunActiveViewModel)
}
