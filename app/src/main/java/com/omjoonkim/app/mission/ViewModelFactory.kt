package com.omjoonkim.app.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omjoonkim.app.mission.di.AppModule
import com.omjoonkim.app.mission.viewmodel.MainViewModelImpl
import com.omjoonkim.app.mission.viewmodel.SearchViewModelImpl

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
