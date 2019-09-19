package com.alexredchets.githubbrowser.main

import android.util.Log
import com.alexredchets.githubbrowser.base.BaseRepository

class MainRepo: BaseRepository() {

    suspend fun loadUser(user: String): User? {
        var response: User? =
            User()
        try {
            response = safeApiCall(
                call = {apiService.getUserAsync(user).await()},
                errorMessage = "Error fetching teams list"
            )
        } catch (e: Exception) {
            Log.e("ERROR", e.message)
        }
        return response
    }

    suspend fun loadRepos(user: String): List<Repo>? {
        var response: List<Repo>? = ArrayList()
        try {
            response = safeApiCall(
                call = {apiService.getReposAsync(user).await()},
                errorMessage = "Error fetching teams list"
            )
        } catch (e: Exception) {
            Log.e("ERROR", e.message)
        }
        return response?.toList()
    }

}