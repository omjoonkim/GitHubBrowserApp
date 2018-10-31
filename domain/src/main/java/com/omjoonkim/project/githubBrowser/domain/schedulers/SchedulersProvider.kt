package com.omjoonkim.project.githubBrowser.domain.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}
