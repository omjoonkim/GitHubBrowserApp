package com.omjoonkim.app.githubBrowserApp.di

import com.omjoonkim.project.githubBrowser.remote.GithubBrowserService
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import com.omjoonkim.project.githubBrowser.remote.model.UserModel
import io.reactivex.Single

class TestDummyGithubBrowserService : GithubBrowserService {
    override fun getUser(userName: String): Single<UserModel> =
        Single.just(
            UserModel("omjoonkim", "")
        )

    override fun getRepos(userName: String): Single<List<RepoModel>> =
        Single.just(
            listOf(
                RepoModel("repo1", "repo1 description", "1"),
                RepoModel("repo2", "repo2 description", "2"),
                RepoModel("repo3", "repo3 description", "3")
            )
        )
}
