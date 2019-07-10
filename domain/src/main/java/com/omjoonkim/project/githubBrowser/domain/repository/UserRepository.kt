package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.User
import io.reactivex.Single

interface UserRepository {
    fun get(userName: String): Single<User>
}
