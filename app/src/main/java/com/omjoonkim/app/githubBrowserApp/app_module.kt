package com.omjoonkim.app.githubBrowserApp

import com.omjoonkim.app.githubBrowserApp.viewmodel.MainViewModel
import com.omjoonkim.app.githubBrowserApp.viewmodel.SearchViewModel
import com.omjoonkim.project.githubBrowser.data.interactor.GithubBrowserDataSource
import com.omjoonkim.project.githubBrowser.data.repository.GithubBrowserRemote
import com.omjoonkim.project.githubBrowser.domain.interactor.usecases.GetUserData
import com.omjoonkim.project.githubBrowser.domain.repository.GitHubBrowserRepository
import com.omjoonkim.project.githubBrowser.domain.schedulers.SchedulersProvider
import com.omjoonkim.project.githubBrowser.remote.GithubBrowserRemoteImpl
import com.omjoonkim.project.githubBrowser.remote.GithubBrowserServiceFactory
import com.omjoonkim.project.githubBrowser.remote.mapper.RepoEntityMapper
import com.omjoonkim.project.githubBrowser.remote.mapper.UserEntityMapper
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val myModule: Module = module {
    viewModel { (id: String) -> MainViewModel(id, get(), get()) }
    viewModel { SearchViewModel(get()) }

    //app
    single { Logger() }
    single { AppSchedulerProvider() as SchedulersProvider }

    //domain
    single { GetUserData(get(), get()) }

    //data
    single { GithubBrowserDataSource(get()) as GitHubBrowserRepository }

    //remote
    single { GithubBrowserRemoteImpl(get(), get(), get()) as GithubBrowserRemote }
    single { RepoEntityMapper() }
    single { UserEntityMapper() }
    single {
        GithubBrowserServiceFactory.makeGithubBrowserService(
            BuildConfig.DEBUG,
            "https://api.github.com"
        )
    }
}
