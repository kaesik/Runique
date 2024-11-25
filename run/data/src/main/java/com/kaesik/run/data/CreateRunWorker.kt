package com.kaesik.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kaesik.core.database.dao.RunPendingSyncDao
import com.kaesik.core.database.mappers.toRun
import com.kaesik.core.domain.run.RemoteRunDataSource
import com.kaesik.core.domain.util.RuniqueResult

class CreateRunWorker(
    context: Context,
    private val params: WorkerParameters,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val pendingSyncDao: RunPendingSyncDao,
):CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }
        val pendingRunId = params.inputData.getString(RUN_ID) ?: return Result.failure()
        val pendingRunEntity = pendingSyncDao.getRunPendingSyncEntity(pendingRunId)
            ?: return Result.failure()

        val run = pendingRunEntity.run.toRun()
        return when (val result = remoteRunDataSource.postRun(run, pendingRunEntity.mapPictureBytes)) {
            is RuniqueResult.Error -> {
                result.error.toWorkerResult()
            }
            is RuniqueResult.Success -> {
                pendingSyncDao.deleteRunPendingSyncEntity(pendingRunId)
                Result.success()
            }
        }
    }

    companion object {
        const val RUN_ID = "RUN_ID"
    }
}
