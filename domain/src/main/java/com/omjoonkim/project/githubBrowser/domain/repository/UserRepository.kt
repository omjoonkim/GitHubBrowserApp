package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.User

interface UserRepository {
    suspend fun get(userName: String): User
}
