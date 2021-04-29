package com.omjoonkim.project.githubBrowser.domain.interactor.usecases

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.exception.NetworkException
import com.omjoonkim.project.githubBrowser.domain.exception.RateLimitException
import com.omjoonkim.project.githubBrowser.domain.interactor.CoroutineUseCase
import com.omjoonkim.project.githubBrowser.domain.repository.ForkRepository
import com.omjoonkim.project.githubBrowser.domain.repository.RepoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetRepoDetail(
        private val repoRepository: RepoRepository,
        private val forkRepository: ForkRepository,
        dispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetRepoDetail.Result, GetRepoDetail.Param>(dispatcher) {
    override suspend fun buildUseCase(params: Param): CoroutineUseCase.Result<Result> =
            try {
                coroutineScope {
                    val repo = async { repoRepository.get(params.userName, params.id) }
                    val forks = async { forkRepository.gets(params.userName, params.id) }
                    CoroutineUseCase.Result.Success(Result(repo.await(), forks.await()))
                }
            } catch (e: Throwable) {
                if (e is NetworkException && e.code == 403)
                    CoroutineUseCase.Result.Fail((RateLimitException()))
                else CoroutineUseCase.Result.Fail(e)
            }


    data class Param(
            val userName: String,
            val id: String
    )

    data class Result(
            val repo: Repo,
            val forks: List<Fork>
    )
}