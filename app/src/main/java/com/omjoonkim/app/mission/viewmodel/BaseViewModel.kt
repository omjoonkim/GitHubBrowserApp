package com.omjoonkim.app.mission.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.util.Pair
import com.omjoonkim.app.mission.rx.LifecycleTransformer
import com.omjoonkim.app.mission.ui.BaseActivity
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.*
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiresActivityViewModel(val value: KClass<out BaseViewModel>)

abstract class RootViewModel {
    abstract fun onCreate(context: Context, savedInstanceState: Bundle?)
    abstract fun onResume(view: BaseActivity<BaseViewModel>)
    abstract fun onDestroy()
}

open class BaseViewModel(context: Context) : RootViewModel() {
    private val viewChange = PublishSubject.create<BaseActivity<BaseViewModel>>()
    val view: Observable<BaseActivity<BaseViewModel>> = viewChange
    val error: PublishSubject<Throwable> = PublishSubject.create<Throwable>()
    val intent: PublishSubject<Intent> = PublishSubject.create<Intent>()

    @CallSuper
    override fun onCreate(context: Context, savedInstanceState: Bundle?) {
        error.bindToLifeCycle()
                .subscribe { it.printStackTrace() }
    }

    fun intent(intent: Intent) = this.intent.onNext(intent)

    @CallSuper
    override fun onResume(view: BaseActivity<BaseViewModel>) {
        onTakeView(view)
    }

    @CallSuper
    override fun onDestroy() {
        viewChange.onComplete()
    }

    private fun onTakeView(view: BaseActivity<BaseViewModel>) {
        viewChange.onNext(view)
    }

    fun <T> bindToLifecycle(): LifecycleTransformer<T> =
            LifecycleTransformer(view.switchMap { v -> v.lifecycle().map { e -> Pair.create(v, e) } }
                    .filter { ve -> isFinished(ve.first, ve.second) })

    fun <T> Observable<T>.bindToLifeCycle(): Observable<T> = compose(bindToLifecycle())
    fun <T> Flowable<T>.bindToLifeCycle(): Flowable<T> = compose(bindToLifecycle())
    fun <T> Single<T>.bindToLifeCycle(): Single<T> = compose(bindToLifecycle())
    fun <T> Maybe<T>.bindToLifeCycle(): Maybe<T> = compose(bindToLifecycle())
    fun Completable.bindToLifeCycle(): Completable = compose(bindToLifecycle<Any>())

    private fun isFinished(view: BaseActivity<*>, event: ActivityEvent): Boolean =
            if (view is BaseActivity<*>)
                event === ActivityEvent.DESTROY && (view as BaseActivity).isFinishing
            else
                event === ActivityEvent.DESTROY
}


