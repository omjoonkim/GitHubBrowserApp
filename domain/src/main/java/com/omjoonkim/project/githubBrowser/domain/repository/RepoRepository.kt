package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Repo

interface RepoRepository {
    suspend fun gets(userName: String): List<Repo>
    suspend fun get(userName: String, id: String): Repo
}
