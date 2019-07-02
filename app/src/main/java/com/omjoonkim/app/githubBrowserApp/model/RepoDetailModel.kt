package com.omjoonkim.app.githubBrowserApp.model

import com.omjoonkim.app.githubBrowserApp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RepoDetailModel {

    fun getRepoDetail(userName: String, id: String) =
        ApiClient.githubBrowserService.getRepo(userName, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
