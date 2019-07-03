package com.omjoonkim.app.githubBrowserApp.network

import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/repos/{userName}/{repo}")
    fun getRepo(
        @Path("userName") userName: String,
        @Path("repo") repoName: String
    ): Single<RepoModel>

    @GET("/repos/{userName}/{repo}/forks")
    fun getForks(
        @Path("userName") userName: String,
        @Path("repo") repoName: String
    ): Single<List<ForkModel>>
}
