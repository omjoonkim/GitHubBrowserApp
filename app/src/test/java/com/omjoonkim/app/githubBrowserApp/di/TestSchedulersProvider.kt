package com.omjoonkim.app.githubBrowserApp.di

import com.omjoonkim.project.githubBrowser.domain.schedulers.SchedulersProvider
import io.reactivex.schedulers.Schedulers


class TestSchedulerProvider : SchedulersProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()
}
