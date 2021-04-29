package com.omjoonkim.app.githubBrowserApp.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

interface Input
interface Output

abstract class BaseViewModel<Input : com.omjoonkim.app.githubBrowserApp.viewmodel.Input, Output : com.omjoonkim.app.githubBrowserApp.viewmodel.Output> : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

