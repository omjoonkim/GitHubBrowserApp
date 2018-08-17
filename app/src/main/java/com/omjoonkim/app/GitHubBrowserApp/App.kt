package com.omjoonkim.app.GitHubBrowserApp

import android.app.Application
import com.google.gson.Gson
import com.omjoonkim.app.GitHubBrowserApp.data.GitHubDataRepository
import com.omjoonkim.app.GitHubBrowserApp.data.GitHubRepositoryType
import com.omjoonkim.app.GitHubBrowserApp.network.APIClient
import com.omjoonkim.app.GitHubBrowserApp.network.APIClientType
import com.omjoonkim.app.GitHubBrowserApp.network.APIService
import com.omjoonkim.app.GitHubBrowserApp.viewmodel.MainViewModel
import com.omjoonkim.app.GitHubBrowserApp.viewmodel.MainViewModelImpl
import com.omjoonkim.app.GitHubBrowserApp.viewmodel.SearchViewModel
import com.omjoonkim.app.GitHubBrowserApp.viewmodel.SearchViewModelImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(myModule)
        )
    }
}

val myModule: Module = module {
    viewModel { MainViewModelImpl(get()) as MainViewModel }
    viewModel { SearchViewModelImpl() as SearchViewModel }
    single { Gson() }
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    factory {
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    factory {
        Retrofit.Builder()
            .client(get())
            .baseUrl(APIService.EndPoint.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(APIService::class.java)
    }
    factory { APIClient(get()) as APIClientType }
    factory { GitHubDataRepository(get()) as GitHubRepositoryType }
    factory { Environment(get(), get()) }
}
