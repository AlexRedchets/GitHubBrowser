package com.alexredchets.githubbrowser.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexredchets.githubbrowser.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: BaseViewModel() {

    private val repo = MainRepo()

    private val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    private val repos: MutableLiveData<List<Repo>> by lazy {
        MutableLiveData<List<Repo>>()
    }

    fun loadUser(userName: String): LiveData<User> {
        if (netManager.isConnected) {
            coroutinesManager.getUIScope().launch {
                val userValue = withContext(coroutinesManager.getIOScope().coroutineContext) {
                    repo.loadUser(userName)
                }
                if (userValue != null) {
                    loadRepos(userName)
                }
                user.value = userValue
            }
        } else {
            user.value = User()
        }
        return user
    }

    private fun loadRepos(userName: String): LiveData<List<Repo>> {
        if (netManager.isConnected) {
            coroutinesManager.getUIScope().launch {
                val list = withContext(coroutinesManager.getIOScope().coroutineContext) {
                    repo.loadRepos(userName)
                }

                repos.value = list
            }
        } else {
            user.value = User()
        }
        return repos
    }

    fun getUser(): LiveData<User> = user

    fun getRepos(): LiveData<List<Repo>> = repos

}