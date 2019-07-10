package com.omjoonkim.project.githubBrowser.domain.interactor.usecases

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.exception.NetworkException
import com.omjoonkim.project.githubBrowser.domain.exception.RateLimitException
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
) : SingleUseCase<Pair<Repo, List<Fork>>, Pair<String, String>>(
    schedulersProvider) {
    override fun buildUseCaseSingle(params: Pair<String, String>)
        : Single<Pair<Repo, List<Fork>>> =
        Single.zip(
            repoRepository.get(params.first, params.second),
            forkRepository.gets(params.first, params.second),
            BiFunction { t1: Repo, t2: List<Fork> ->
                t1 to t2
            }
        ).onErrorResumeNext {
            if (it is NetworkException && it.code == 403)
                Single.error(RateLimitException())
            else Single.error(it)
        }
}
