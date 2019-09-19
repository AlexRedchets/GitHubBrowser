package com.alexredchets.githubbrowser.calls

import kotlinx.coroutines.*
import java.lang.Exception

class CoroutinesManager {

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)
    private var ioScope = CoroutineScope(Dispatchers.IO + job)

    fun cancel() = job.cancel()

    fun getUIScope(): CoroutineScope {
        if (!job.isActive || job.isCancelled) {
            job = Job()
        }
        if (!uiScope.isActive) {
            uiScope = CoroutineScope(Dispatchers.Main + job)
        }
        return uiScope
    }

    fun getIOScope(): CoroutineScope {
        if (!job.isActive || job.isCancelled) {
            job = Job()
        }
        if (!ioScope.isActive) {
            ioScope = CoroutineScope(Dispatchers.IO + job)
        }
        return ioScope
    }
}

sealed class CoroutinesResult<out T: Any> {
    data class Success<out T: Any>(val data: T): CoroutinesResult<T>()
    data class Failure(val exception: Exception): CoroutinesResult<Nothing>()
}