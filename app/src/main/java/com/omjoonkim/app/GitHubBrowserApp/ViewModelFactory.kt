package com.omjoonkim.app.GitHubBrowserApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omjoonkim.app.GitHubBrowserApp.di.AppModule
import com.omjoonkim.app.GitHubBrowserApp.viewmodel.MainViewModelImpl

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val appModule: AppModule
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            MainViewModelImpl::class.java -> MainViewModelImpl(appModule.environment) as T
            else -> throw IllegalStateException()
        }
}
