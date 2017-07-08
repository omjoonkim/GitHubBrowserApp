package com.omjoonkim.app.mission.viewmodel

import android.content.Context
import android.content.Intent
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import com.omjoonkim.app.mission.rx.neverError
import com.trello.rxlifecycle2.kotlin.bind
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

class MainViewModel(context: Context) : BaseViewModel(context), OutPuts {

    val outPuts = this
    private val user = PublishSubject.create<User>()
    private val repos = PublishSubject.create<List<Repo>>()

    init {
        intent.filter { it.action == Intent.ACTION_VIEW }
                .map { it.data.path.substring(1) }
                .flatMapMaybe { enviorment.gitHubDataRepository.getUserInfo(it).neverError(error) }
                .doOnNext { user.onNext(it) }
                .flatMapMaybe { enviorment.gitHubDataRepository.getUserRepos(it.name).neverError(error) }
                .bindToLifeCycle()
                .subscribe(repos)
    }

    override fun user(): Observable<User> = user

    override fun repos(): Observable<List<Repo>> = repos
}

interface OutPuts {
    fun user(): Observable<User>
    fun repos(): Observable<List<Repo>>
}
