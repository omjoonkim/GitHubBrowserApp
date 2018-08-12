package com.omjoonkim.app.GitHubBrowserApp.data

import com.omjoonkim.app.GitHubBrowserApp.network.model.Repo
import com.omjoonkim.app.GitHubBrowserApp.network.model.User
import io.reactivex.Single

interface GitHubRepositoryType {
    fun getUserInfo(userName: String): Single<User>
    fun getUserRepos(userName: String): Single<List<Repo>>
}
