package com.omjoonkim.project.githubBrowser.data.interactor

import com.omjoonkim.project.githubBrowser.data.source.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserDataRepository(
    private val remote: GithubBrowserRemote,
    private val dispatcher : CoroutineDispatcher
) : UserRepository {
    override suspend fun get(userName: String): User = withContext(dispatcher){
        remote.getUserInfo(userName)
    }
}
