package com.omjoonkim.project.githubBrowser.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GithubBrowserServiceFactory {

    fun makeGithubBrowserService(debug: Boolean, baseUrl: String): GithubBrowserService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(debug)
        )
        return makeGithubBrowserService(baseUrl, okHttpClient, Gson())
    }

    private fun makeGithubBrowserService(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): GithubBrowserService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(GithubBrowserService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private fun makeLoggingInterceptor(debug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (debug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}
