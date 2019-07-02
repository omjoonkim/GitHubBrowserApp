package com.omjoonkim.app.githubBrowserApp

import com.omjoonkim.app.githubBrowserApp.ui.repo.RepoDetailPresenter
import com.omjoonkim.app.githubBrowserApp.ui.repo.RepoDetailView
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val myModule: Module = module {
    //presentation
    viewModel { (id: String) -> MainViewModel(id, get(), get()) }
    viewModel { SearchViewModel(get()) }
    factory { (view: RepoDetailView) -> RepoDetailPresenter(view) }

    //app
    single { Logger() }
    single { AppSchedulerProvider() as SchedulersProvider }

    //domain
    factory { GetUserData(get(), get()) }

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
