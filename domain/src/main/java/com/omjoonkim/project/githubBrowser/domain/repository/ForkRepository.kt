package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Fork

interface ForkRepository {
    suspend fun gets(userName: String, id: String): List<Fork>
}
