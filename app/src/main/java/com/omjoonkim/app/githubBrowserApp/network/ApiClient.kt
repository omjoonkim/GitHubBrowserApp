package com.omjoonkim.app.githubBrowserApp.network

import com.google.gson.Gson
import com.omjoonkim.app.githubBrowserApp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val githubBrowserService: GithubService

    init {
        githubBrowserService = makeGithubBrowserService(BuildConfig.DEBUG, "https://api.github.com")
    }

    fun makeGithubBrowserService(debug: Boolean, baseUrl: String): GithubService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(debug)
        )
        return makeGithubBrowserService(baseUrl, okHttpClient, Gson())
    }

    private fun makeGithubBrowserService(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): GithubService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(GithubService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) :OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun makeLoggingInterceptor(debug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (debug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}
