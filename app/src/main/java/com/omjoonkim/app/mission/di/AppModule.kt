package com.omjoonkim.app.mission.di

import com.google.gson.Gson
import com.omjoonkim.app.mission.App
import com.omjoonkim.app.mission.Environment
import com.omjoonkim.app.mission.data.GitHubDataRepository
import com.omjoonkim.app.mission.data.GitHubRepositoryType
import com.omjoonkim.app.mission.network.APIClient
import com.omjoonkim.app.mission.network.APIClientType
import com.omjoonkim.app.mission.network.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AppModule(private val app: App) {

    val applicationContexxt get() = app.applicationContext

    private val gson by lazy { Gson() }

    private val loggingInterceptor by lazy { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val apiService: APIService by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(APIService.EndPoint.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService::class.java)
    }

    private val apiClient: APIClientType by lazy { APIClient(apiService) }

    private val gitHubDataRepository: GitHubRepositoryType by lazy { GitHubDataRepository(apiClient) }

    val environment by lazy {
        Environment(
            gitHubDataRepository,
            gson
        )
    }
}
