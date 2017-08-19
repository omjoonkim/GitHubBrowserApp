package com.omjoonkim.app.mission.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import com.omjoonkim.app.mission.App
import com.omjoonkim.app.mission.Environment
import com.omjoonkim.app.mission.data.GitHubDataRepository
import com.omjoonkim.app.mission.data.GitHubRepositoryType
import com.omjoonkim.app.mission.network.APIClient
import com.omjoonkim.app.mission.network.APIClientType
import com.omjoonkim.app.mission.network.APIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideApiClient(apiService: APIService): APIClientType = APIClient(apiService)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

    @Provides
    @Singleton
    fun provideApiService(gson: Gson, okhttpCLient: OkHttpClient): APIService =
            Retrofit.Builder()
                    .client(okhttpCLient)
                    .baseUrl(APIService.EndPoint.baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(APIService::class.java)

    @Provides
    @Singleton
    fun gitHubDataRepository(apiClient: APIClientType): GitHubRepositoryType = GitHubDataRepository(apiClient)

    @Provides
    @Singleton
    fun enviorment(apiClient: APIClientType,
                   gitHubRepositoryType: GitHubRepositoryType,
                   gson: Gson): Environment =
            Environment(apiClient,
                    gitHubRepositoryType,
                    gson)
}