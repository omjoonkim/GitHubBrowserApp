package com.omjoonkim.app.githubBrowserApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omjoonkim.app.githubBrowserApp.Logger
import com.omjoonkim.app.githubBrowserApp.call
import com.omjoonkim.app.githubBrowserApp.error.Error
import com.omjoonkim.app.githubBrowserApp.error.UnExpected
import com.omjoonkim.app.githubBrowserApp.rx.Parameter
import com.omjoonkim.app.githubBrowserApp.rx.neverError
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.interactor.CoroutineUseCase
import com.omjoonkim.project.githubBrowser.domain.interactor.usecases.GetUserData
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModelImpl(
        searchedUserName: String,
        private val getUserData: GetUserData,
        logger: Logger
) : BaseViewModel<MainViewModelInputs, MainViewModelOutPuts>(), MainViewModel {

    private val state = MutableLiveData<MainViewState>()
    private val showErrorToast = MutableLiveData<String>()
    private val goProfileActivity = MutableLiveData<String>()
    private val goRepoDetailActivity = MutableLiveData<Pair<String, String>>()
    private val finish = MutableLiveData<Unit>()

    init {
        state.value = MainViewState()
        viewModelScope.launch {
            val requestListData = getUserData.get(GetUserData.Param(searchedUserName))
            state.value = when (requestListData) {
                is CoroutineUseCase.Result.Success -> state.value?.copy(
                        showLoading = false,
                        title = requestListData.data.user.name,
                        user = requestListData.data.user,
                        repos = requestListData.data.repos
                )
                is CoroutineUseCase.Result.Fail -> {
                    showErrorToast.value = UnExpected.errorText
                    finish.call()
                    state.value?.copy(showLoading = false)
                }
            }
        }
    }

    override fun clickUser(user: User) {
        goProfileActivity.value = user.name
    }

    override fun clickRepo(user: User, repo: Repo) {
        goRepoDetailActivity.value = user.name to repo.name
    }

    override fun clickHomeButton() {
        finish.call()
    }

    override fun state() = state
    override fun showErrorToast() = showErrorToast
    override fun goProfileActivity() = goProfileActivity
    override fun goRepoDetailActivity() = goRepoDetailActivity
    override fun finish() = finish
}

interface MainViewModel : MainViewModelInputs, MainViewModelOutPuts

interface MainViewModelInputs : Input {
    fun clickUser(user: User)
    fun clickRepo(user: User, repo: Repo)
    fun clickHomeButton()
}

interface MainViewModelOutPuts : Output {
    fun state(): LiveData<MainViewState>
    fun showErrorToast(): LiveData<String>
    fun goProfileActivity(): LiveData<String>
    fun goRepoDetailActivity(): LiveData<Pair<String, String>>
    fun finish(): LiveData<Unit>
}

data class MainViewState(
        val showLoading: Boolean = true,
        val title: String = "",
        val user: User? = null,
        val repos: List<Repo> = emptyList()
)
