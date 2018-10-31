package com.omjoonkim.project.githubBrowser.remote

import com.omjoonkim.project.githubBrowser.data.repository.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.remote.mapper.RepoEntityMapper
import com.omjoonkim.project.githubBrowser.remote.mapper.UserEntityMapper
import io.reactivex.Single

class GithubBrowserRemoteImpl(
    private val githubBrowserAppService: GithubBrowserService,
    private val userEntityMapper: UserEntityMapper,
    private val repoEntityMapper: RepoEntityMapper
) : GithubBrowserRemote {
    override fun getUserInfo(userName: String): Single<User> = githubBrowserAppService
        .getUserInfo(userName)
        .map { userEntityMapper.mapFromRemote(it) }

    override fun getUserRepos(userName: String): Single<List<Repo>> = githubBrowserAppService
        .getUserRepos(userName)
        .map { it.map { repoEntityMapper.mapFromRemote(it) } }

}
