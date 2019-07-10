package com.omjoonkim.app.githubBrowserApp.repository

import com.omjoonkim.app.githubBrowserApp.network.GithubService
import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForkRepositoryImpl(
    private val api: GithubService
) : ForkRepository {

    override fun gets(userName: String, id: String): Single<List<ForkModel>> =
        api.getForks(userName, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
