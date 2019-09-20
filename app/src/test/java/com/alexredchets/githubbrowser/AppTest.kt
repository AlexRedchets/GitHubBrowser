package com.alexredchets.githubbrowser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexredchets.githubbrowser.base.appModule
import com.alexredchets.githubbrowser.main.MainRepo
import com.alexredchets.githubbrowser.main.MainViewModel
import com.alexredchets.githubbrowser.main.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertEquals
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@RunWith(JUnit4::class)
class AppTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepo: MainRepo

    private val user = User()

    @Before
    fun setUp() {
        startKoin {
            modules(appModule)
        }
        mainRepo = MainRepo()
        mainViewModel = MainViewModel()
        initUser()
    }

    @After
    fun after() {
        stopKoin()
    }

    private fun initUser() {
        user.apply {
            name = "alexredchets"
            avatar_url = "https://avatars2.githubusercontent.com/u/18506231?v=4"
        }
    }

    @Test
    fun getUserTest() {
        runBlocking {
            mainRepo.loadUser("alexredchets")
        }
        mainViewModel.getUser().observeOnce {
            assertEquals(it, user)
        }
    }

    @Test
    fun getReposTest() {
        runBlocking {
            mainRepo.loadRepos("alexredchets")
        }
        mainViewModel.getRepos().observeOnce {
            assertEquals(it.size, 30)
        }

    }
}