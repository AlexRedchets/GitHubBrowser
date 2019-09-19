package com.alexredchets.githubbrowser.calls

import com.alexredchets.githubbrowser.main.Repo
import com.alexredchets.githubbrowser.main.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/users/{user}")
    fun getUserAsync(@Path("user") user: String): Deferred<Response<User>>

    @GET("/users/{userId}/repos")
    fun getReposAsync(@Path("userId") userId: String): Deferred<Response<List<Repo>>>
}