package com.omjoonkim.app.githubBrowserApp.repository

import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import io.reactivex.Single

interface ForkRepository {
    fun gets(userName: String, id: String): Single<List<ForkModel>>
}
