package com.kaesik.core.database.di

import androidx.room.Room
import com.kaesik.core.database.RoomLocalRunDataSource
import com.kaesik.core.database.RunDatabase
import com.kaesik.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RunDatabase::class.java,
            "run.db"
        ).build()
    }
    single { get<RunDatabase>().runDao }
    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}
