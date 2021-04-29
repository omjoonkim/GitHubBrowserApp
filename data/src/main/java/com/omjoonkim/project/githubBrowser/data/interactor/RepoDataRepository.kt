package com.omjoonkim.project.githubBrowser.data.interactor

import com.omjoonkim.project.githubBrowser.data.source.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.repository.RepoRepository
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RepoDataRepository(
        private val remote: GithubBrowserRemote,
        private val dispatcher: CoroutineDispatcher
) : RepoRepository {

    override suspend fun gets(userName: String): List<Repo> = withContext(dispatcher) {
        remote.getRepos(userName)
    }

    override suspend fun get(userName: String, id: String): Repo = withContext(dispatcher) {
        remote.getRepo(userName, id)
    }
}
