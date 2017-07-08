package com.omjoonkim.app.mission.rx

import io.reactivex.*
import org.reactivestreams.Publisher
import java.util.concurrent.CancellationException
import javax.annotation.ParametersAreNonnullByDefault

@ParametersAreNonnullByDefault
class LifecycleTransformer<T> internal constructor(internal val observable: Observable<*>) : ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer {
    init {
        checkNotNull(observable, { "observable == null" })
    }

    override fun apply(upstream: Observable<T>): ObservableSource<T> = upstream.takeUntil(observable)

    override fun apply(upstream: Flowable<T>): Publisher<T> = upstream.takeUntil(observable.toFlowable(BackpressureStrategy.LATEST))

    override fun apply(upstream: Single<T>): SingleSource<T> = upstream.takeUntil(observable.firstOrError())

    override fun apply(upstream: Maybe<T>): MaybeSource<T> = upstream.takeUntil(observable.firstElement())

    override fun apply(upstream: Completable): CompletableSource = Completable.ambArray(upstream, observable.flatMapCompletable({ Completable.error(CancellationException()) })).onErrorResumeNext { Completable.never() }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val that = other as LifecycleTransformer<*>

        return observable == that.observable
    }

    override fun hashCode(): Int = observable.hashCode()

    override fun toString(): String = "LifecycleTransformer{observable=$observable}"
}