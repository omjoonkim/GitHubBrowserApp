package com.omjoonkim.app.mission

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.app.AppComponentFactory
import androidx.lifecycle.*
import com.omjoonkim.app.mission.di.AppModule
import com.omjoonkim.app.mission.ui.BaseActivity
import com.omjoonkim.app.mission.ui.main.MainActivity
import com.omjoonkim.app.mission.ui.search.SearchActivity
import com.omjoonkim.app.mission.viewmodel.MainViewModelImpl
import com.omjoonkim.app.mission.viewmodel.SearchViewModelImpl

class ComponentFactory : AppComponentFactory() {

    private lateinit var appModule: AppModule
    private lateinit var viewModelFactory: ViewModelFactory

    override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
        val app = super.instantiateApplicationCompat(cl, className) as App
        appModule = AppModule(app)
        viewModelFactory = ViewModelFactory(appModule)
        return app
    }

    override fun instantiateActivityCompat(cl: ClassLoader, className: String, intent: Intent?): Activity {
        val activity = super.instantiateActivityCompat(cl, className, intent)
        return if (activity is BaseActivity<*>) {
            val viewModelProvider = ViewModelProviders.of(activity, viewModelFactory)
            return when (activity) {
                is MainActivity -> activity.preBindViewModel(
                    viewModelProvider.get(MainViewModelImpl::class.java)
                )
                is SearchActivity -> activity.preBindViewModel(
                    viewModelProvider.get(SearchViewModelImpl::class.java)
                )
                else -> throw IllegalArgumentException()
            }
        } else activity
    }

    private inline fun <reified VIEW_MODEL> BaseActivity<VIEW_MODEL>.preBindViewModel(viewModel: VIEW_MODEL)
        where VIEW_MODEL : com.omjoonkim.app.mission.viewmodel.ViewModel = apply {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                bind(viewModel)
            }
        })
    }
}
