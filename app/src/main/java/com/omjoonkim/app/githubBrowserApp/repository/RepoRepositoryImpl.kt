package com.omjoonkim.app.githubBrowserApp.repository

import com.omjoonkim.app.githubBrowserApp.network.GithubService
import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoRepositoryImpl(
    private val api: GithubService
) : RepoRepository {

    override fun getRepo(
        userName: String,
        id: String
    ): Single<RepoModel> =
        api.getRepo(userName, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getForks(
        userName: String,
        id: String
    ): Single<List<ForkModel>> =
        api.getForks(userName, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
