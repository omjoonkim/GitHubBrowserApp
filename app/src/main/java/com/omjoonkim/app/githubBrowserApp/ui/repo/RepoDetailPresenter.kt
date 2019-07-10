package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.rx.printStackTrace
import com.omjoonkim.app.githubBrowserApp.ui.BasePresenter
import com.omjoonkim.project.githubBrowser.domain.interactor.usecases.GetRepoDetail

class RepoDetailPresenter(
    view: RepoDetailView,
    private val getRepoDetail: GetRepoDetail
) : BasePresenter<RepoDetailView>(view) {

    fun onCreate(userName: String, repoName: String) {
        view.setToolbarTitle(userName)
        compositeDisposable.add(
            getRepoDetail.get(
                userName to repoName
            ).subscribe({ (repo, forks) ->
                view.setName(repo.name)
                view.setDescription(repo.description ?: "")
                view.setStarCount(repo.starCount)
                view.refreshForks(forks)
            }, ::printStackTrace)
        )
    }
}
