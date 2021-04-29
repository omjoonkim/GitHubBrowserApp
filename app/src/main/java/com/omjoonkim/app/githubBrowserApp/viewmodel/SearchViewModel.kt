package com.omjoonkim.app.githubBrowserApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omjoonkim.app.githubBrowserApp.Logger

class SearchViewModelImpl(
        logger: Logger
) : BaseViewModel<Input, Output>(), SearchViewModel {

    private val state = MutableLiveData<SearchViewState>()
    private val goResultActivity = MutableLiveData<String>()

    init {
        state.value = SearchViewState("", false)
    }

    override fun name(name: String) {
        state.value = state.value?.copy(keyword = name, enableSearchButton = name.isNotEmpty())
    }

    override fun clickSearchButton() {
        goResultActivity.value = state.value?.keyword
    }

    override fun state() = state
    override fun goResultActivity() = goResultActivity
}

interface SearchViewModel : SearchViewModelInPuts, SearchViewModelOutPuts

interface SearchViewModelInPuts : Input {
    fun name(name: String)
    fun clickSearchButton()
}

interface SearchViewModelOutPuts : Output {
    fun state(): LiveData<SearchViewState>
    fun goResultActivity(): LiveData<String>
}

data class SearchViewState(
        val keyword: String,
        val enableSearchButton: Boolean
)
