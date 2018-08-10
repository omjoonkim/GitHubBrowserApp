package com.omjoonkim.app.mission.rx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*
import io.reactivex.subjects.Subject
import org.intellij.lang.annotations.Flow

enum class Parameter {
    CLICK, NULL, SUCCESS, EVENT
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

fun <T> Observable<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this.toFlowable(io.reactivex.BackpressureStrategy.DROP))
fun <T> Single<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this.toFlowable())
fun <T> Maybe<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this.toFlowable())
fun <T> Completable.toLiveData() = LiveDataReactiveStreams.fromPublisher(this.toFlowable<T>())
fun <T> Flowable<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this)

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = observe({ lifecycleOwner.lifecycle }, observer)

fun <T> Observable<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = toLiveData().observe(lifecycleOwner, observer)
fun <T> Single<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = toLiveData().observe(lifecycleOwner, observer)
fun <T> Maybe<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = toLiveData().observe(lifecycleOwner, observer)
fun <T> Completable.bind(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = toLiveData<T>().observe(lifecycleOwner, observer)
fun <T> Flowable<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = toLiveData().observe(lifecycleOwner, observer)
