package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.repository.ForkRepository
import com.omjoonkim.app.githubBrowserApp.repository.RepoRepository
import com.omjoonkim.app.githubBrowserApp.rx.printStackTrace
import com.omjoonkim.app.githubBrowserApp.ui.BasePresenter
import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class RepoDetailPresenter(
    view: RepoDetailView,
    private val repoRepository: RepoRepository,
    private val forkRepository: ForkRepository
) : BasePresenter<RepoDetailView>(view) {

    fun onCreate(userName: String, repoName: String) {
        compositeDisposable.addAll(
            Single.zip(
                repoRepository.get(userName, repoName),
                forkRepository.gets(userName, repoName),
                BiFunction { t1: RepoModel, t2: List<ForkModel> ->
                    t1 to t2
                }
            ).subscribe({ (repo, forks) ->
                view.setName(repo.name)
                view.setDescription(repo.description ?: "")
                view.setStarCount(repo.starCount)
                view.refreshForks(forks)
            }, ::printStackTrace)
        )
    }
}
