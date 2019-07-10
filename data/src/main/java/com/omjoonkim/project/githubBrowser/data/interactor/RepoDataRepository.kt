package com.omjoonkim.project.githubBrowser.data.interactor

import com.omjoonkim.project.githubBrowser.data.source.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.repository.RepoRepository
import io.reactivex.Single

class RepoDataRepository(
    private val remote: GithubBrowserRemote
) : RepoRepository {

    override fun gets(
        userName: String
    ): Single<List<Repo>> = remote.getRepos(userName)

    override fun get(
        userName: String,
        id: String
    ): Single<Repo> = remote.getRepo(userName, id)
}
