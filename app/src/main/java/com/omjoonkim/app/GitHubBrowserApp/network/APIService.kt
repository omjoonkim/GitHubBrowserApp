package com.omjoonkim.app.GitHubBrowserApp.network

import com.omjoonkim.app.GitHubBrowserApp.network.model.Repo
import com.omjoonkim.app.GitHubBrowserApp.network.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    object EndPoint {
        const val baseUrl = "https://api.github.com"
    }

    @GET("/users/{userName}")
    fun getUserInfo(@Path("userName") userName: String): Single<User>

    @GET("/users/{userName}/repos")
    fun getUserRepos(@Path("userName") userName: String): Single<List<Repo>>

}
