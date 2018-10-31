package com.omjoonkim.project.githubBrowser.data.interactor

import com.omjoonkim.project.githubBrowser.data.repository.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.repository.GitHubBrowserRepository
import io.reactivex.Single

class GithubBrowserDataSource(
    private val remote: GithubBrowserRemote
) : GitHubBrowserRepository {
    override fun getUserInfo(userName: String): Single<User> = remote.getUserInfo(userName)

    override fun getUserRepos(userName: String): Single<List<Repo>> = remote.getUserRepos(userName)
}
