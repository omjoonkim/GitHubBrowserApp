package com.omjoonkim.app.githubBrowserApp.repository

import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import io.reactivex.Single

interface RepoRepository {
    fun get(userName: String, id: String): Single<RepoModel>
}
