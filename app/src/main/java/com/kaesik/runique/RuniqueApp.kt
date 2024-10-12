package com.kaesik.runique

import android.app.Application
import com.kaesik.auth.data.di.authDataModule
import com.kaesik.auth.presentation.di.authViewModelModule
import com.kaesik.core.data.BuildConfig
import com.kaesik.core.data.di.coreDataModule
import com.kaesik.runique.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule
            )
        }
    }
}