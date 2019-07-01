package com.omjoonkim.app.githubBrowserApp.di

import com.omjoonkim.app.githubBrowserApp.myModule
import com.omjoonkim.project.githubBrowser.domain.schedulers.SchedulersProvider
import com.omjoonkim.project.githubBrowser.remote.GithubBrowserService
import org.koin.dsl.module

val testModule = module {
    single(override = true) {
        TestSchedulerProvider() as SchedulersProvider
    }
    single(override = true) {
        TestDummyGithubBrowserService() as GithubBrowserService
    }
}

val test_module = listOf(myModule, testModule)
