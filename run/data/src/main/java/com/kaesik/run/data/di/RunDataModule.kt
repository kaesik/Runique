package com.kaesik.run.data.di

import com.kaesik.run.data.CreateRunWorker
import com.kaesik.run.data.DeleteRunWorker
import com.kaesik.run.data.FetchRunsWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)
}
