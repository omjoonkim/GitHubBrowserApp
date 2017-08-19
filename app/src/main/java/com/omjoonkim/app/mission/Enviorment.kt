package com.omjoonkim.app.mission

import com.google.gson.Gson
import com.omjoonkim.app.mission.data.GitHubRepositoryType
import com.omjoonkim.app.mission.network.APIClientType

data class Environment(
        var apiClient: APIClientType,
        var gitHubDataRepository: GitHubRepositoryType,
        var gson: Gson
)