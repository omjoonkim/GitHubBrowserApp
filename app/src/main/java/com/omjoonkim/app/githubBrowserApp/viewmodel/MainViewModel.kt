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
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


class MainViewModel(
    private val getUserData: GetUserData,
    logger: Logger
) : BaseViewModel() {

    private val searchedUserName = PublishSubject.create<String>()
    private val clickedUserName = PublishSubject.create<User>()
    private val onClickHomeButton = PublishSubject.create<Parameter>()
    val input: MainViewModelInputs = object : MainViewModelInputs {
        override fun searchedUserName(userName: String) = this@MainViewModel.searchedUserName.onNext(userName)
        override fun onClickUser(user: User) = clickedUserName.onNext(user)
        override fun onClickHomeButton() = onClickHomeButton.onNext(Parameter.CLICK)
    }

    private val state = MutableLiveData<MainViewState>()
    private val refreshListData = MutableLiveData<Pair<User, List<Repo>>>()
    private val showErrorToast = MutableLiveData<String>()
    private val goProfileActivity = MutableLiveData<String>()
    private val finish = MutableLiveData<Unit>()
    val output = object : MainViewModelOutPuts {
        override fun state() = state
        override fun refreshListData() = refreshListData
        override fun showErrorToast() = showErrorToast
        override fun goProfileActivity() = goProfileActivity
        override fun finish() = finish
    }

    init {
        val showLoading = BehaviorSubject.createDefault(false)
        val error = PublishSubject.create<Throwable>()
        val requestListData = searchedUserName
            .flatMapMaybe {
                getUserData.get(it).neverError(error)
            }.share()
        compositeDisposable.addAll(
            Observables
                .combineLatest(
                    Observable.merge(
                        showLoading,
                        searchedUserName.map { true },
                        requestListData.map { false },
                        error.map { false }
                    ),
                    searchedUserName,
                    ::MainViewState
                ).subscribe(state::setValue, logger::d),
            requestListData.subscribe(refreshListData::setValue, logger::d),
            error
                .map {
                    if (it is Error)
                        it.errorText
                    else UnExpected.errorText
                }.subscribe(showErrorToast::setValue, logger::d),
            clickedUserName.map { it.name }
                .subscribe(goProfileActivity::setValue, logger::d),
            onClickHomeButton.subscribe(finish::call, logger::d)
        )
    }
}

interface MainViewModelInputs : Input {
    fun searchedUserName(userName: String)
    fun onClickUser(user: User)
    fun onClickHomeButton()
}

interface MainViewModelOutPuts : Output {
    fun state(): LiveData<MainViewState>
    fun refreshListData(): LiveData<Pair<User, List<Repo>>>
    fun showErrorToast(): LiveData<String>
    fun goProfileActivity(): LiveData<String>
    fun finish(): LiveData<Unit>
}

data class MainViewState(
    val showLoading: Boolean,
    val title: String
)
