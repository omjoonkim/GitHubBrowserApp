package com.omjoonkim.project.githubBrowser.remote

import com.omjoonkim.project.githubBrowser.data.source.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.remote.mapper.ForkEntityMapper
import com.omjoonkim.project.githubBrowser.remote.mapper.RepoEntityMapper
import com.omjoonkim.project.githubBrowser.remote.mapper.UserEntityMapper
import io.reactivex.Single

class GithubBrowserRemoteImpl(
    private val githubBrowserAppService: GithubBrowserService,
    private val userEntityMapper: UserEntityMapper,
    private val repoEntityMapper: RepoEntityMapper,
    private val forkEntityMapper: ForkEntityMapper
) : GithubBrowserRemote {

    override fun getUserInfo(userName: String): Single<User> = githubBrowserAppService
        .getUser(userName)
        .map { userEntityMapper.mapFromRemote(it) }
        .composeDomain()

    override fun getRepos(userName: String): Single<List<Repo>> = githubBrowserAppService
        .getRepos(userName)
        .map { it.map { repoEntityMapper.mapFromRemote(it) } }
        .composeDomain()

    override fun getRepo(userName: String, id: String): Single<Repo> = githubBrowserAppService
        .getRepo(userName, id)
        .map { repoEntityMapper.mapFromRemote(it) }
        .composeDomain()

    override fun getForks(
        userName: String,
        id: String
    ): Single<List<Fork>> = githubBrowserAppService
        .getForks(userName, id)
        .map { it.map { forkEntityMapper.mapFromRemote(it) } }
        .composeDomain()
}
