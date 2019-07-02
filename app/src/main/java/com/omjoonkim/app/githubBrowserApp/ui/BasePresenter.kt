package com.omjoonkim.app.githubBrowserApp.ui

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T : BaseView>(protected val view: T) {

    protected val compositeDisposable = CompositeDisposable()

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}
