package com.omjoonkim.project.githubBrowser.domain.interactor.usecases

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.interactor.SingleUseCase
import com.omjoonkim.project.githubBrowser.domain.repository.ForkRepository
import com.omjoonkim.project.githubBrowser.domain.repository.RepoRepository
import com.omjoonkim.project.githubBrowser.domain.schedulers.SchedulersProvider
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class GetRepoDetail(
    private val repoRepository: RepoRepository,
    private val forkRepository: ForkRepository,
    schedulersProvider: SchedulersProvider
) : SingleUseCase<Pair<Repo, List<Fork>>, Pair<String,String>>(schedulersProvider) {

    override fun buildUseCaseSingle(params: Pair<String,String>): Single<Pair<Repo, List<Fork>>> = Single.zip(
        repoRepository.getRepo(params.first, params.second),
        forkRepository.getForks(params.first, params.second),
        BiFunction{ t1 : Repo, t2 : List<Fork> ->
            t1 to t2
        }
    )
}
