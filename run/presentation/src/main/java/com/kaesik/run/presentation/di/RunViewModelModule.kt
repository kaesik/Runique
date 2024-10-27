package com.kaesik.run.presentation.di

import com.kaesik.run.presentation.run_active.RunActiveViewModel
import com.kaesik.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val runViewModelModule = module {
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::RunActiveViewModel)
}
