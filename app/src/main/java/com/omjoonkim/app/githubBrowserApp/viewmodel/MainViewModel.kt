package com.omjoonkim.app.githubBrowserApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omjoonkim.app.githubBrowserApp.Logger
import com.omjoonkim.app.githubBrowserApp.call
import com.omjoonkim.app.githubBrowserApp.error.Error
import com.omjoonkim.app.githubBrowserApp.error.UnExpected
import com.omjoonkim.app.githubBrowserApp.rx.Parameter
import com.omjoonkim.app.githubBrowserApp.rx.neverError
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.interactor.usecases.GetUserData
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject


class MainViewModel(
    searchedUserName: String,
    private val getUserData: GetUserData,
    logger: Logger
) : BaseViewModel() {

    private val clickUser = PublishSubject.create<User>()
    private val clickRepo = PublishSubject.create<Pair<User, Repo>>()
    private val clickHomeButton = PublishSubject.create<Parameter>()
    val input: MainViewModelInputs = object : MainViewModelInputs {
        override fun clickUser(user: User) = clickUser.onNext(user)
        override fun clickRepo(user: User, repo: Repo) = clickRepo.onNext(user to repo)
        override fun clickHomeButton() = clickHomeButton.onNext(Parameter.CLICK)
    }

    private val state = MutableLiveData<MainViewState>()
    private val refreshListData = MutableLiveData<Pair<User, List<Repo>>>()
    private val showErrorToast = MutableLiveData<String>()
    private val goProfileActivity = MutableLiveData<String>()
    private val goRepoDetailActivity = MutableLiveData<Pair<String, String>>()
    private val finish = MutableLiveData<Unit>()
    val output = object : MainViewModelOutPuts {
        override fun state() = state
        override fun refreshListData() = refreshListData
        override fun showErrorToast() = showErrorToast
        override fun goProfileActivity() = goProfileActivity
        override fun goRepoDetailActivity() = goRepoDetailActivity
        override fun finish() = finish
    }

    init {
        val error = PublishSubject.create<Throwable>()
        val userName = Observable.just(searchedUserName).share()
        val requestListData = userName.flatMapMaybe {
            getUserData.get(it).neverError(error)
        }.share()
        compositeDisposable.addAll(
            Observables
                .combineLatest(
                    Observable.merge(
                        requestListData.map { false },
                        error.map { false }
                    ).startWith(true),
                    userName,
                    ::MainViewState
                ).subscribe(state::setValue, logger::d),
            requestListData.subscribe(refreshListData::setValue, logger::d),
            error.map {
                if (it is Error)
                    it.errorText
                else UnExpected.errorText
            }.subscribe(showErrorToast::setValue, logger::d),
            clickUser.map { it.name }.subscribe(goProfileActivity::setValue, logger::d),
            clickRepo.map { it.first.name to it.second.name }.subscribe(goRepoDetailActivity::setValue, logger::d),
            clickHomeButton.subscribe(finish::call, logger::d)
        )
    }
}

interface MainViewModelInputs : Input {
    fun clickUser(user: User)
    fun clickRepo(user: User, repo: Repo)
    fun clickHomeButton()
}

interface MainViewModelOutPuts : Output {
    fun state(): LiveData<MainViewState>
    fun refreshListData(): LiveData<Pair<User, List<Repo>>>
    fun showErrorToast(): LiveData<String>
    fun goProfileActivity(): LiveData<String>
    fun goRepoDetailActivity(): LiveData<Pair<String, String>>
    fun finish(): LiveData<Unit>
}

data class MainViewState(
    val showLoading: Boolean,
    val title: String
)
