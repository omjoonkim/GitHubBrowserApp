package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import io.reactivex.Single

interface RepoRepository {
    fun getRepos(userName: String): Single<List<Repo>>
    fun getRepo(userName: String, id: String): Single<Repo>
}
