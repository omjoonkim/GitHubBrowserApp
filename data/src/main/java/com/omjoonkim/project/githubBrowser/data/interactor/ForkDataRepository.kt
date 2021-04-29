package com.omjoonkim.project.githubBrowser.data.interactor

import com.omjoonkim.project.githubBrowser.data.source.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.repository.ForkRepository
import com.omjoonkim.project.githubBrowser.domain.repository.RepoRepository
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForkDataRepository(
    private val remote: GithubBrowserRemote,
    private val dispatcher : CoroutineDispatcher
) : ForkRepository {

    override suspend fun gets(userName: String, id: String): List<Fork> =  withContext(dispatcher){
        remote.getForks(userName, id)
    }
}
