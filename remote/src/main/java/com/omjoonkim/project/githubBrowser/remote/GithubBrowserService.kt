package com.omjoonkim.project.githubBrowser.remote

import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import com.omjoonkim.project.githubBrowser.remote.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubBrowserService {

    @GET("/users/{userName}")
    suspend fun getUser(@Path("userName") userName: String): UserModel

    @GET("/users/{userName}/repos")
    suspend fun getRepos(@Path("userName") userName: String): List<RepoModel>

    @GET("/repos/{userName}/{repo}")
    suspend fun getRepo(@Path("userName") userName: String, @Path("repo") repoName: String): RepoModel

    @GET("/repos/{userName}/{repo}/forks")
    suspend fun getForks(@Path("userName") userName: String, @Path("repo") repoName: String): List<ForkModel>
}
