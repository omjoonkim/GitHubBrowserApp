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

    override suspend fun getUserInfo(userName: String): User = githubBrowserAppService
            .getUser(userName).let(userEntityMapper::mapFromRemote)

    override suspend fun getRepos(userName: String): List<Repo> = githubBrowserAppService
            .getRepos(userName)
            .let { it.map { repoEntityMapper.mapFromRemote(it) } }

    override suspend fun getRepo(userName: String, id: String): Repo = githubBrowserAppService
            .getRepo(userName, id)
            .let(repoEntityMapper::mapFromRemote)

    override suspend fun getForks(
            userName: String,
            id: String
    ): List<Fork> = githubBrowserAppService
            .getForks(userName, id)
            .let { it.map { forkEntityMapper.mapFromRemote(it) } }
}
