package com.alexredchets.githubbrowser.base

import androidx.lifecycle.ViewModel
import com.alexredchets.githubbrowser.calls.CoroutinesManager
import com.alexredchets.githubbrowser.calls.NetManager
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel: ViewModel(), KoinComponent {

    val coroutinesManager: CoroutinesManager by inject()
    val netManager: NetManager by inject()

    override fun onCleared() {
        super.onCleared()
        coroutinesManager.cancel()
    }

}