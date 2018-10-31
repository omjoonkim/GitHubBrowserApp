package com.omjoonkim.project.githubBrowser.domain.interactor.usecases

import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.interactor.SingleUseCase
import com.omjoonkim.project.githubBrowser.domain.repository.GitHubBrowserRepository
import com.omjoonkim.project.githubBrowser.domain.schedulers.SchedulersProvider
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class GetUserData(
    private val gitHubRepository: GitHubBrowserRepository,
    schedulersProvider: SchedulersProvider
) : SingleUseCase<Pair<User, List<Repo>>, String>(
    schedulersProvider
) {
    override fun buildUseCaseSingle(userName: String): Single<Pair<User, List<Repo>>> =
        Single.zip(
            gitHubRepository.getUserInfo(userName),
            gitHubRepository.getUserRepos(userName),
            BiFunction<User, List<Repo>, Pair<User, List<Repo>>> { t1, t2 ->
                Pair(t1, t2.sortedByDescending { it.starCount })
            }
        )

}
