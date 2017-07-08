package com.omjoonkim.app.mission.network

import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class APIClient(val apiService: APIService) : APIClientType {

    override fun getUserInfo(userName: String): Single<User> =
            apiService.getUserInfo(userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    override fun getUserRepos(userName: String): Single<List<Repo>> =
            apiService.getUserRepos(userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}