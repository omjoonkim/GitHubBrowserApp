package com.omjoonkim.app.mission.network

import com.omjoonkim.app.mission.error.NotConnectedNetwork
import com.omjoonkim.app.mission.error.NotFoundUser
import com.omjoonkim.app.mission.error.UnExpected
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class APIClient(private val apiService: APIService) : APIClientType {

    override fun getUserInfo(userName: String): Single<User> =
        apiService.getUserInfo(userName)
            .onErrorResumeNext {
                Single.error(
                    if (it is HttpException && it.code() == 404)
                        NotFoundUser
                    else if (it is IOException)
                        NotConnectedNetwork
                    else
                        UnExpected
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getUserRepos(userName: String): Single<List<Repo>> =
        apiService.getUserRepos(userName)
            .onErrorResumeNext {
                Single.error(
                    if (it is HttpException && it.code() == 404)
                        NotFoundUser
                    else if (it is IOException)
                        NotConnectedNetwork
                    else
                        UnExpected
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
