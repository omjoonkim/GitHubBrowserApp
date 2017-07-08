package com.omjoonkim.app.mission.data

import com.omjoonkim.app.mission.network.APIClientType
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import io.reactivex.Single

class GitHubDataRepository(val apiClient: APIClientType) : GitHubRepositoryType {

    override fun getUserInfo(userName: String): Single<User> = apiClient.getUserInfo(userName)

    override fun getUserRepos(userName: String): Single<List<Repo>> = apiClient.getUserRepos(userName)
}