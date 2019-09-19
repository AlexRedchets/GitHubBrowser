package com.alexredchets.githubbrowser.main

data class User(
    val name: String = "",
    val avatar_url: String = ""
)

data class Repo(val forks: String,
                val updated_at: String,
                val stargazers_count: String,
                val name: String,
                val description: String)

