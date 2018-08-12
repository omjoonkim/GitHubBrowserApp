package com.omjoonkim.app.GitHubBrowserApp

import com.google.gson.Gson
import com.omjoonkim.app.GitHubBrowserApp.data.GitHubRepositoryType

data class Environment(
    var gitHubDataRepository: GitHubRepositoryType,
    var gson: Gson
)
