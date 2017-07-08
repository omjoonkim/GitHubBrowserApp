package com.omjoonkim.app.mission.viewmodel

import android.content.Context
import android.content.Intent
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import com.omjoonkim.app.mission.rx.neverError
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel(context: Context) : BaseViewModel(context), OutPuts {

    val outPuts = this
    private val listDatas = PublishSubject.create<Pair<User, List<Repo>>>()
    private val loading = BehaviorSubject.create<Boolean>()

    init {
        intent.filter { it.action == Intent.ACTION_VIEW }
                .map { it.data.path.substring(1) }
                .doOnNext { loading.onNext(true) }
                .flatMapMaybe {
                    enviorment.gitHubDataRepository.getUserInfo(it).neverError(error)
                            .zipWith(enviorment.gitHubDataRepository.getUserRepos(it).neverError(error),
                                    BiFunction<User, List<Repo>, Pair<User, List<Repo>>> { t1, t2 ->
                                        Pair(t1, t2)
                                    }
                            )
                }
                .doOnNext { loading.onNext(false) }
                .bindToLifeCycle()
                .subscribe(listDatas)

        error.doOnNext { loading.onNext(false) }
    }

    override fun listDatas(): Observable<Pair<User, List<Repo>>> = listDatas
    override fun loading(): Observable<Boolean> = loading
}

interface OutPuts {
    fun listDatas(): Observable<Pair<User, List<Repo>>>
    fun loading(): Observable<Boolean>
}
