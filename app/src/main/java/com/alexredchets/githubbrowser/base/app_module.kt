package com.alexredchets.githubbrowser.base

import android.content.Context
import com.alexredchets.githubbrowser.BuildConfig
import com.alexredchets.githubbrowser.calls.Api
import com.alexredchets.githubbrowser.calls.CoroutinesManager
import com.alexredchets.githubbrowser.calls.NetManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { createOkHttpClient() }
    single {
        createWebService(
            get(),
            BuildConfig.BASE_URL
        )
    }
    single { createCoroutinesManager() }
    factory { provideNetManager(androidContext()) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createWebService(okHttpClient: OkHttpClient, baseUrl: String): Api {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    return retrofit.create(Api::class.java)
}

fun createCoroutinesManager() = CoroutinesManager()

fun provideNetManager(context: Context) = NetManager(context)