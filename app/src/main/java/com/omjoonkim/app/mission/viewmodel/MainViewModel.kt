package com.omjoonkim.app.mission.viewmodel

import android.util.Log
import com.omjoonkim.app.mission.Environment
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import com.omjoonkim.app.mission.rx.neverError
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


interface MainViewModel : ViewModel {
    val input: MainViewModelInputs
    val output: MainViewModelOutPuts
}

class MainViewModelImpl(private val environment: Environment) : BaseViewModel(), MainViewModel {

    private val refreshListDatas = PublishSubject.create<Pair<User, List<Repo>>>()
    private val loading = BehaviorSubject.create<Boolean>()
    private val searchedUserName = BehaviorSubject.create<String>()
    private val error = PublishSubject.create<Throwable>()
    private val actionBarInit = BehaviorSubject.create<String>()

    override val input: MainViewModelInputs = object : MainViewModelInputs {
        override fun searchedUserName(userName: String) = this@MainViewModelImpl.searchedUserName.onNext(userName)
    }
    override val output = object : MainViewModelOutPuts {
        override fun refreshListDatas(): Observable<Pair<User, List<Repo>>> = refreshListDatas
        override fun loading(): Observable<Boolean> = loading
        override fun error(): Observable<Throwable> = error
        override fun actionBarInit(): Observable<String> = actionBarInit
    }

    init {
        compositeDisposable.addAll(
            searchedUserName
                .doOnNext {
                    Log.e(it,it+"1")
                }
                .subscribe(actionBarInit::onNext),
            searchedUserName
                .doOnNext {
                    Log.e(it,it)
                }
                .doOnNext { loading.onNext(true) }
                .flatMapMaybe {
                    environment.gitHubDataRepository
                        .getUserInfo(it).neverError(error)
                        .zipWith(
                            environment.gitHubDataRepository
                                .getUserRepos(it).neverError(error),
                            BiFunction<User, List<Repo>, Pair<User, List<Repo>>> { t1, t2 ->
                                Pair(t1, t2.sortedByDescending { it.starCount })
                            }
                        )
                }
                .doOnNext { loading.onNext(false) }
                .subscribe(refreshListDatas::onNext),
            error
                .map { false }
                .subscribe(loading::onNext)
        )
    }
}

interface MainViewModelInputs : Input {
    fun searchedUserName(userName: String)
}

interface MainViewModelOutPuts : Output {
    fun actionBarInit(): Observable<String>
    fun refreshListDatas(): Observable<Pair<User, List<Repo>>>
    fun loading(): Observable<Boolean>
    fun error(): Observable<Throwable>
}
