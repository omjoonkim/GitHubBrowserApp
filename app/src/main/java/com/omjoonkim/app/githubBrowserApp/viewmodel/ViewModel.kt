package com.omjoonkim.app.githubBrowserApp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

interface Input
interface Output

abstract class BaseViewModel : ViewModel(){
    protected val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        Log.e(this::class.java.name,"onClear")
    }
}

