package com.kaesik.run.data.di

import com.kaesik.core.domain.run.SyncRunScheduler
import com.kaesik.run.data.CreateRunWorker
import com.kaesik.run.data.DeleteRunWorker
import com.kaesik.run.data.FetchRunsWorker
import com.kaesik.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}
