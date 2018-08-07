package com.omjoonkim.app.mission.network

import android.accounts.NetworkErrorException
import com.omjoonkim.app.mission.error.NotConnectedNetworkError
import com.omjoonkim.app.mission.error.NotFoundUserError
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class APIClient(val apiService: APIService) : APIClientType {

    override fun getUserInfo(userName: String): Single<User> =
            apiService.getUserInfo(userName)
                    .onErrorResumeNext {
                        Single.error(
                                if (it is HttpException && it.code() == 404)
                                    NotFoundUserError()
                                else if (it is IOException)
                                    NotConnectedNetworkError()
                                else
                                    it
                        )
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    override fun getUserRepos(userName: String): Single<List<Repo>> =
            apiService.getUserRepos(userName)
                    .onErrorResumeNext {
                        Single.error(
                                if (it is HttpException && it.code() == 404)
                                    NotFoundUserError()
                                else if (it is IOException)
                                    NotConnectedNetworkError()
                                else
                                    it
                        )
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}
