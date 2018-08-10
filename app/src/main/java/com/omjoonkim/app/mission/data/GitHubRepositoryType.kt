package com.omjoonkim.app.mission.data

import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import io.reactivex.Single

interface GitHubRepositoryType {
    fun getUserInfo(userName: String): Single<User>
    fun getUserRepos(userName: String): Single<List<Repo>>
}
