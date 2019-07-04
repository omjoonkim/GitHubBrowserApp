package com.omjoonkim.project.githubBrowser.remote

import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import com.omjoonkim.project.githubBrowser.remote.model.UserModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubBrowserService {

    @GET("/users/{userName}")
    fun getUser(
        @Path("userName") userName: String
    ): Single<UserModel>

    @GET("/users/{userName}/repos")
    fun getRepos(
        @Path("userName") userName: String
    ): Single<List<RepoModel>>

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
