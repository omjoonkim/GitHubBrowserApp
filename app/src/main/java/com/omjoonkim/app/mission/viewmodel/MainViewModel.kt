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

class MainViewModel(context: Context) : BaseViewModel(context) {

    private val refreshListDatas = PublishSubject.create<Pair<User, List<Repo>>>()
    private val loading = BehaviorSubject.create<Boolean>()

    val outPuts = object : MainViewModelOutPuts {
        override fun refreshListDatas(): Observable<Pair<User, List<Repo>>> = refreshListDatas
        override fun loading(): Observable<Boolean> = loading
    }

    init {
        intent.filter { it.action == Intent.ACTION_VIEW }
                .map { it.data.path.substring(1) }
                .doOnNext { loading.onNext(true) }
                .flatMapMaybe {
                    enviorment.gitHubDataRepository
                            .getUserInfo(it).neverError(error)
                            .zipWith(
                                    enviorment.gitHubDataRepository
                                            .getUserRepos(it).neverError(error),
                                    BiFunction<User, List<Repo>, Pair<User, List<Repo>>> { t1, t2 ->
                                        Pair(t1, t2.sortedByDescending { it.starCount })
                                    }
                            )
                }
                .doOnNext { loading.onNext(false) }
                .bindToLifeCycle()
                .subscribe(refreshListDatas)

        error.doOnNext { loading.onNext(false) }
    }
}

interface MainViewModelOutPuts {
    fun refreshListDatas(): Observable<Pair<User, List<Repo>>>
    fun loading(): Observable<Boolean>
}
