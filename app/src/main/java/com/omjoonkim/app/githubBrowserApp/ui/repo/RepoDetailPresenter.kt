package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.repository.RepoRepository
import com.omjoonkim.app.githubBrowserApp.ui.BasePresenter

class RepoDetailPresenter(
    view: RepoDetailView,
    private val repoRepository: RepoRepository
) : BasePresenter<RepoDetailView>(view) {

    fun onCreate(userName: String, repoName: String) {
            repoRepository.getRepo(userName, repoName)
                .subscribe({
                    view.setName(it.name)
                    view.setDescription(it.description ?: "")
                    view.setStarCount(it.starCount)
                }, {
                    it.printStackTrace()
                })
    }
}
