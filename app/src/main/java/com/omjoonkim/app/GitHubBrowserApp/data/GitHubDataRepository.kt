package com.omjoonkim.app.GitHubBrowserApp.data

import com.omjoonkim.app.GitHubBrowserApp.network.APIClientType
import com.omjoonkim.app.GitHubBrowserApp.network.model.Repo
import com.omjoonkim.app.GitHubBrowserApp.network.model.User
import io.reactivex.Single

class GitHubDataRepository(private val apiClient: APIClientType) : GitHubRepositoryType {
    override fun getUserInfo(userName: String): Single<User> = apiClient.getUserInfo(userName)
    override fun getUserRepos(userName: String): Single<List<Repo>> = apiClient.getUserRepos(userName)
}
