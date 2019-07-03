package com.omjoonkim.app.githubBrowserApp.repository

import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import io.reactivex.Single

interface RepoRepository {
    fun getRepo(userName: String, id: String): Single<RepoModel>
    fun getForks(userName: String, id: String): Single<List<ForkModel>>
}
