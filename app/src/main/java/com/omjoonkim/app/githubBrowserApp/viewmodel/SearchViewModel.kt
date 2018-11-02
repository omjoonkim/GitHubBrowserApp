package com.omjoonkim.app.githubBrowserApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omjoonkim.app.githubBrowserApp.Logger
import com.omjoonkim.app.githubBrowserApp.rx.Parameter
import com.omjoonkim.app.githubBrowserApp.rx.takeWhen
import io.reactivex.subjects.PublishSubject

class SearchViewModel(
    logger: Logger
) : BaseViewModel() {

    private val name = PublishSubject.create<String>()
    private val clickSearchButton = PublishSubject.create<Parameter>()
    val input = object : SearchViewModelInPuts {
        override fun name(name: String) =
            this@SearchViewModel.name.onNext(name)
        override fun clickSearchButton() =
            this@SearchViewModel.clickSearchButton.onNext(Parameter.CLICK)
    }

    private val state = MutableLiveData<SearchViewState>()
    private val goResultActivity = MutableLiveData<String>()
    val output = object : SearchViewModelOutPuts {
        override fun state() = state
        override fun goResultActivity() = goResultActivity
    }

    init {
        compositeDisposable.addAll(
            name.map { SearchViewState(it.isNotEmpty()) }
                .subscribe(state::setValue, logger::d),
            name.takeWhen(clickSearchButton) { _, t2 -> t2 }
                .subscribe(goResultActivity::setValue, logger::d)
        )
    }
}

interface SearchViewModelInPuts : Input {
    fun name(name: String)
    fun clickSearchButton()
}

interface SearchViewModelOutPuts : Output {
    fun state(): LiveData<SearchViewState>
    fun goResultActivity(): LiveData<String>
}

data class SearchViewState(
    val enableSearchButton: Boolean
)
