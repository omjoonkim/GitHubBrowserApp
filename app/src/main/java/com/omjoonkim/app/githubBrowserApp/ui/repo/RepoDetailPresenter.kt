package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.ui.BasePresenter
import com.omjoonkim.project.githubBrowser.domain.exception.NetworkException
import com.omjoonkim.project.githubBrowser.domain.exception.RateLimitException
import com.omjoonkim.project.githubBrowser.domain.interactor.CoroutineUseCase
import com.omjoonkim.project.githubBrowser.domain.interactor.usecases.GetRepoDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RepoDetailPresenter(
        view: RepoDetailView,
        private val getRepoDetail: GetRepoDetail
) : BasePresenter<RepoDetailView>(view) {

    private var job: Job = Job()

    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    fun onCreate(userName: String, repoName: String) {
        view.setToolbarTitle(userName)
        scope.launch {
            val repo = getRepoDetail.get(GetRepoDetail.Param(userName, repoName))
            when (repo) {
                is CoroutineUseCase.Result.Success -> {
                    view.setName(repo.data.repo.name)
                    view.setDescription(repo.data.repo.description ?: "")
                    view.setStarCount(repo.data.repo.starCount)
                    view.refreshForks(repo.data.forks)
                }
                is CoroutineUseCase.Result.Fail -> handleException(repo.t)
            }
        }
    }

    private fun handleException(throwable: Throwable) {
        when (throwable) {
            is RateLimitException -> view.toastRateLimitError()
            is NetworkException -> view.toastNetworkError()
            else -> view.toastUnexpectedError()
        }
    }
}
