package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import io.reactivex.Single

interface GitHubBrowserRepository {
    fun getUserInfo(userName: String): Single<User>
    fun getUserRepos(userName: String): Single<List<Repo>>
}
