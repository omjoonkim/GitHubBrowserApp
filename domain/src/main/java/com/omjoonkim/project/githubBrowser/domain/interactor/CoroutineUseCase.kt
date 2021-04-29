package com.omjoonkim.project.githubBrowser.domain.interactor

import kotlinx.coroutines.CoroutineDispatcher

abstract class CoroutineUseCase<T, in Params>(
        private val dispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun buildUseCase(params: Params): Result<T>

    suspend fun get(params: Params) = buildUseCase(params)


    sealed class Result<T> {
        data class Success<T>(val data: T) : Result<T>()
        data class Fail<T>(val t: Throwable) : Result<T>()
    }
}
