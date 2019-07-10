package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import io.reactivex.Single

interface RepoRepository {
    fun gets(userName: String): Single<List<Repo>>
    fun get(userName: String, id: String): Single<Repo>
}
