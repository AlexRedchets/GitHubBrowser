package com.alexredchets.githubbrowser

import android.app.Application
import com.alexredchets.githubbrowser.base.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use Koin logger
            printLogger()
            // declare Android context
            androidContext(this@App)
            modules(appModule)
        }
    }

}