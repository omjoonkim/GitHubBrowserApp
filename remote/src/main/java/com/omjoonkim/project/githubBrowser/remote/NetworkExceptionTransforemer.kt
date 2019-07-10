package com.omjoonkim.project.githubBrowser.remote

import com.omjoonkim.project.githubBrowser.domain.exception.NetworkException
import io.reactivex.*
import retrofit2.HttpException

internal class NetworkExceptionSingleTransformer<T> : SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): SingleSource<T> =
        upstream.onErrorResumeNext {
            Single.error(
                if (it is HttpException)
                    NetworkException(it.message(), it.code())
                else it
            )
        }
}

internal class NetworkExceptionCompletableTransformer : CompletableTransformer {
    override fun apply(upstream: Completable): CompletableSource = upstream.onErrorResumeNext {
        Completable.error(
            if (it is HttpException)
                NetworkException(it.message(), it.code())
            else it
        )
    }
}

internal fun <T> Single<T>.composeDomain() =
    compose(NetworkExceptionSingleTransformer())

internal fun Completable.composeDomain() = compose(NetworkExceptionCompletableTransformer())
