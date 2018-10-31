package com.omjoonkim.project.githubBrowser.data.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import io.reactivex.Single

interface GithubBrowserRemote{
    fun getUserInfo(userName: String): Single<User>
    fun getUserRepos(userName: String): Single<List<Repo>>
}
