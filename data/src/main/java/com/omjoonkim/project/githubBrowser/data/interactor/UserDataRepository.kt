package com.omjoonkim.project.githubBrowser.data.interactor

import com.omjoonkim.project.githubBrowser.data.source.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.repository.UserRepository
import io.reactivex.Single

class UserDataRepository(
    private val remote: GithubBrowserRemote
) : UserRepository {
    override fun get(userName: String): Single<User> = remote.getUserInfo(userName)
}
