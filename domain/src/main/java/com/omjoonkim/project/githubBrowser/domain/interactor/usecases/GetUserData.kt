package com.omjoonkim.project.githubBrowser.domain.interactor.usecases

import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.exception.NetworkException
import com.omjoonkim.project.githubBrowser.domain.exception.RateLimitException
import com.omjoonkim.project.githubBrowser.domain.interactor.CoroutineUseCase
import com.omjoonkim.project.githubBrowser.domain.interactor.SingleUseCase
import com.omjoonkim.project.githubBrowser.domain.repository.RepoRepository
import com.omjoonkim.project.githubBrowser.domain.repository.UserRepository
import com.omjoonkim.project.githubBrowser.domain.schedulers.SchedulersProvider
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetUserData(
        private val userRepository: UserRepository,
        private val repoRepository: RepoRepository,
        dispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetUserData.Result, GetUserData.Param>(dispatcher) {

    override suspend fun buildUseCase(param: Param): CoroutineUseCase.Result<Result> = try {
        coroutineScope {
            val user = async { userRepository.get(param.userName) }
            val repos = async { repoRepository.gets(param.userName) }

            CoroutineUseCase.Result.Success(Result(user.await(), repos.await().sortedByDescending { it.starCount }))
        }
    } catch (e: Throwable) {
        if (e is NetworkException && e.code == 403)
            CoroutineUseCase.Result.Fail((RateLimitException()))
        else CoroutineUseCase.Result.Fail(e)
    }

    data class Param(val userName: String)

    data class Result(
            val user: User,
            val repos: List<Repo>
    )
}
