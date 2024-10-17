package com.kaesik.auth.presentation.di

import com.kaesik.auth.presentation.login.LoginViewModel
import com.kaesik.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}
