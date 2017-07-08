package com.omjoonkim.app.mission.viewmodel

import android.content.Context
import android.content.Intent
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import com.omjoonkim.app.mission.rx.neverError
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

class MainViewModel(context: Context) : BaseViewModel(context), OutPuts {

    val outPuts = this
    private val listDatas = PublishSubject.create<Pair<User, List<Repo>>>()

    init {
        intent.filter { it.action == Intent.ACTION_VIEW }
                .map { it.data.path.substring(1) }
                .flatMapMaybe {
                    enviorment.gitHubDataRepository.getUserInfo(it).neverError(error)
                            .zipWith(enviorment.gitHubDataRepository.getUserRepos(it).neverError(error),
                                    BiFunction<User, List<Repo>, Pair<User, List<Repo>>> { t1, t2 ->
                                        Pair(t1, t2)
                                    }
                            )
                }
                .bindToLifeCycle()
                .subscribe(listDatas)

    }

    override fun listDatas(): Observable<Pair<User, List<Repo>>> = listDatas
}

interface OutPuts {
    fun listDatas(): Observable<Pair<User, List<Repo>>>
}
