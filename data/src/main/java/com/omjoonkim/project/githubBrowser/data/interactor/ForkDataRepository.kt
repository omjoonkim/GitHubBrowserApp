package com.omjoonkim.project.githubBrowser.data.interactor

import com.omjoonkim.project.githubBrowser.data.source.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.domain.repository.ForkRepository
import com.omjoonkim.project.githubBrowser.domain.repository.RepoRepository
import io.reactivex.Single

class ForkDataRepository(
    private val remote: GithubBrowserRemote
) : ForkRepository {

    override fun getForks(
        userName: String,
        id: String
    ): Single<List<Fork>> = remote.getForks(userName, id)
}
