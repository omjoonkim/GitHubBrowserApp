package com.omjoonkim.project.githubBrowser.data.source

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import io.reactivex.Single

interface GithubBrowserRemote{
    fun getUserInfo(userName: String): Single<User>
    fun getRepos(userName: String): Single<List<Repo>>
    fun getRepo(userName: String, id: String): Single<Repo>
    fun getForks(userName: String, id: String): Single<List<Fork>>
}
