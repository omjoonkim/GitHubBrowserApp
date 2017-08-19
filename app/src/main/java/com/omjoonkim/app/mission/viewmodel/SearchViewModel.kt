package com.omjoonkim.app.mission.viewmodel

import android.content.Context
import com.omjoonkim.app.mission.rx.Parameter
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(context: Context) : BaseViewModel(context) {

    private val name = PublishSubject.create<String>()
    private val clickSearchButton = PublishSubject.create<Parameter>()
    val inPut = object : SearchViewModelInPuts {
        override fun name(name: String) =
                this@SearchViewModel.name.onNext(name)

        override fun clickSearchButton(parameter: Parameter) =
                this@SearchViewModel.clickSearchButton.onNext(parameter)
    }

    private val setEnabledSearchButton = PublishSubject.create<Boolean>()
    private val goResultActivity = PublishSubject.create<String>()
    val outPut = object : SearchViewModelOutPuts {
        override fun setEnabledSearchButton(): Observable<Boolean> = setEnabledSearchButton

        override fun goResultActivity(): Observable<String> = goResultActivity
    }

    init {
        name.map { it.isNotEmpty() }.bindToLifeCycle()
                .subscribe(setEnabledSearchButton)

        name.compose<String> { clickSearchButton.withLatestFrom(it, BiFunction { _, t2 -> t2 }) }
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .bindToLifeCycle()
                .subscribe(goResultActivity)
    }
}

interface SearchViewModelInPuts {
    fun name(name: String)
    fun clickSearchButton(parameter: Parameter)
}

interface SearchViewModelOutPuts {
    fun setEnabledSearchButton(): Observable<Boolean>
    fun goResultActivity(): Observable<String>
}