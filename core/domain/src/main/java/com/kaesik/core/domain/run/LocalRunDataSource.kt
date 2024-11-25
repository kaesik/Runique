package com.kaesik.core.domain.run

import com.kaesik.core.domain.util.DataError
import com.kaesik.core.domain.util.RuniqueResult
import kotlinx.coroutines.flow.Flow

typealias RunId = String

interface LocalRunDataSource {
    fun getRuns(): Flow<List<Run>>
    suspend fun upsertRun(run: Run): RuniqueResult<RunId, DataError.Local>
    suspend fun upsertRuns(runs: List<Run>): RuniqueResult<List<RunId>, DataError.Local>
    suspend fun deleteRun(id: RunId)
    suspend fun deleteAllRuns()
}
