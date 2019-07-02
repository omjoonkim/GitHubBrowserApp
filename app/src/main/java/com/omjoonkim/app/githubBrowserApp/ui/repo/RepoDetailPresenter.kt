package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.model.RepoDetailModel
import com.omjoonkim.app.githubBrowserApp.ui.BasePresenter

class RepoDetailPresenter(
    view: RepoDetailView
) : BasePresenter<RepoDetailView>(view) {

    fun onCreate(userName: String, repoName: String) {
        compositeDisposable.add(
            RepoDetailModel.getRepoDetail(userName, repoName)
                .subscribe({
                    view.setName(it.name)
                    view.setDescription(it.description ?: "")
                    view.setStarCount(it.starCount)
                }, {
                    it.printStackTrace()
                })
        )

    }
}
