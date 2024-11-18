package com.kaesik.runique

import android.app.Application
import com.kaesik.auth.data.di.authDataModule
import com.kaesik.auth.presentation.di.authViewModelModule
import com.kaesik.core.data.BuildConfig
import com.kaesik.core.data.di.coreDataModule
import com.kaesik.core.database.di.databaseModule
import com.kaesik.run.location.di.locationModule
import com.kaesik.run.network.di.networkModule
import com.kaesik.run.presentation.di.runPresentationModule
import com.kaesik.runique.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueApp: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

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
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule
            )
        }
    }
}
