package com.omjoonkim.app.mission.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


interface ViewModel

interface Input
interface Output

object Empty : Input, Output

abstract class BaseViewModel : ViewModel(){
    protected val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

