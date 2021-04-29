package com.omjoonkim.project.githubBrowser.data.source

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User

interface GithubBrowserRemote{
    suspend fun getUserInfo(userName: String): User
    suspend fun getRepos(userName: String): List<Repo>
    suspend fun getRepo(userName: String, id: String): Repo
    suspend fun getForks(userName: String, id: String): List<Fork>
}
