package com.omjoonkim.app.githubBrowserApp.rx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import io.reactivex.*
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.Subject

enum class Parameter {
    CLICK, EMPTY, SUCCESS, EVENT
}

fun <T> Observable<T>.handleToError(action: Subject<Throwable>? = null): Observable<T> = doOnError { action?.onNext(it) }
fun <T> Observable<T>.neverError(): Observable<T> = onErrorResumeNext { _: Throwable -> Observable.empty() }
fun <T> Observable<T>.neverError(action: Subject<Throwable>? = null): Observable<T> = handleToError(action).neverError()

fun <T> Single<T>.handleToError(action: Subject<Throwable>?): Single<T> = doOnError { action?.onNext(it) }
fun <T> Single<T>.neverError(): Maybe<T> = toMaybe().neverError()
fun <T> Single<T>.neverError(action: Subject<Throwable>? = null): Maybe<T> = handleToError(action).neverError()

fun <T> Maybe<T>.handleToError(action: Subject<Throwable>? = null): Maybe<T> = doOnError { action?.onNext(it) }
fun <T> Maybe<T>.neverError(): Maybe<T> = onErrorResumeNext(onErrorComplete())
fun <T> Maybe<T>.neverError(action: Subject<Throwable>? = null): Maybe<T> = handleToError(action).neverError()

fun Completable.handleToError(action: Subject<Throwable>? = null): Completable = doOnError { action?.onNext(it) }
fun Completable.neverError(): Completable = onErrorResumeNext { it.printStackTrace();Completable.never() }
fun Completable.neverError(action: Subject<Throwable>? = null): Completable = handleToError(action).neverError()

fun <T> Flowable<T>.handleToError(action: Subject<Throwable>? = null): Flowable<T> = doOnError { action?.onNext(it) }
fun <T> Flowable<T>.neverError(): Flowable<T> = onErrorResumeNext { _: Throwable -> Flowable.empty() }
fun <T> Flowable<T>.neverError(action: Subject<Throwable>? = null): Flowable<T> = handleToError(action).neverError()

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = observe({ lifecycleOwner.lifecycle }, observer)

fun <T1, T2, R> Observable<T1>.takeWhen(observable: Observable<T2>, biFunction: (T2, T1) -> R): Observable<R> = compose<R> {
    observable.withLatestFrom(it, BiFunction { t1, t2 -> biFunction.invoke(t1, t2) })
}


fun printStackTrace(t : Throwable) = t.printStackTrace()
