package com.kaesik.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaesik.core.database.dao.AnalyticsDao
import com.kaesik.core.database.dao.RunDao
import com.kaesik.core.database.dao.RunPendingSyncDao
import com.kaesik.core.database.entity.DeletedRunSyncEntity
import com.kaesik.core.database.entity.RunEntity
import com.kaesik.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class
    ],
    version = 1
)
abstract class RunDatabase: RoomDatabase() {
    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
    abstract val analyticsDao: AnalyticsDao
}
