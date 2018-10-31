package com.omjoonkim.project.githubBrowser.remote

import com.omjoonkim.project.githubBrowser.remote.model.RepoModel
import com.omjoonkim.project.githubBrowser.remote.model.UserModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubBrowserService {

    @GET("/users/{userName}")
    fun getUserInfo(@Path("userName") userName: String): Single<UserModel>

    @GET("/users/{userName}/repos")
    fun getUserRepos(@Path("userName") userName: String): Single<List<RepoModel>>
}
