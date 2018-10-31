package com.omjoonkim.project.githubBrowser.domain.interactor

import com.omjoonkim.project.githubBrowser.domain.schedulers.SchedulersProvider
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleUseCase<T, in Params>(
    private val schedulersProvider: SchedulersProvider
) {
    protected abstract fun buildUseCaseSingle(params: Params): Single<T>

    fun get(params: Params) = buildUseCaseSingle(params)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
}
