package com.alexredchets.githubbrowser.base

import android.util.Log.d
import com.alexredchets.githubbrowser.calls.Api
import com.alexredchets.githubbrowser.calls.CoroutinesResult
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response
import java.io.IOException

open class BaseRepository: KoinComponent {

    val apiService: Api by inject()

    suspend fun <T: Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is CoroutinesResult.Success ->
                data = result.data
            is CoroutinesResult.Failure -> {
                d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }
        return data
    }

    private suspend fun <T: Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String): CoroutinesResult<T> {
        val response = call.invoke()
        return if (response.isSuccessful) {
            CoroutinesResult.Success(response.body()!!)
        } else {
            CoroutinesResult.Failure(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
        }
    }
}