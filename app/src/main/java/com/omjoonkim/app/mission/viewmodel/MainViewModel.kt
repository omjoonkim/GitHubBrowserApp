package com.omjoonkim.app.mission.viewmodel

import android.content.Context
import android.content.Intent
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainViewModel(context: Context) : BaseViewModel(context), OutPuts {

    val outPuts = this
    private val user = PublishSubject.create<User>()
    private val repos = PublishSubject.create<List<Repo>>()

    init {
        intent.filter { it.action == Intent.ACTION_VIEW }
                .map { it.data.path.substring(1) }
                .flatMapSingle { enviorment.gitHubDataRepository.getUserInfo(it) }
                .doOnNext { user.onNext(it) }
                .flatMapSingle { enviorment.gitHubDataRepository.getUserRepos(it.name) }
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
