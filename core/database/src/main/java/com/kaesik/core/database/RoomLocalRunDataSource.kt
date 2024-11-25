package com.kaesik.core.database

import android.database.sqlite.SQLiteFullException
import com.kaesik.core.database.dao.RunDao
import com.kaesik.core.database.mappers.toRun
import com.kaesik.core.database.mappers.toRunEntity
import com.kaesik.core.domain.run.LocalRunDataSource
import com.kaesik.core.domain.run.Run
import com.kaesik.core.domain.run.RunId
import com.kaesik.core.domain.util.DataError
import com.kaesik.core.domain.util.RuniqueResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(
    private val runDao: RunDao
): LocalRunDataSource {

    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns()
            .map { runEntities ->
                runEntities.map { it.toRun() }
            }
    }

    override suspend fun upsertRun(run: Run): RuniqueResult<RunId, DataError.Local> {
        return try {
            val entity = run.toRunEntity()
            runDao.upsertRun(entity)
            RuniqueResult.Success(entity.id)
        } catch (e: SQLiteFullException) {
            RuniqueResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): RuniqueResult<List<RunId>, DataError.Local> {
        return try {
            val entities = runs.map { it.toRunEntity() }
            runDao.upsertRuns(entities)
            RuniqueResult.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            RuniqueResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: RunId) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}
