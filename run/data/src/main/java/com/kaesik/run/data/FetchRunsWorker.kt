package com.kaesik.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kaesik.core.domain.run.RunRepository
import com.kaesik.core.domain.util.DataError
import com.kaesik.core.domain.util.RuniqueResult

class FetchRunsWorker(
    context: Context,
    params: WorkerParameters,
    private val runRepository: RunRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }
        return when (val result = runRepository.fetchRuns()) {
            is RuniqueResult.Error -> {
                result.error.toWorkerResult()
            }
            is RuniqueResult.Success -> {
                Result.success()
            }
        }
    }
}
