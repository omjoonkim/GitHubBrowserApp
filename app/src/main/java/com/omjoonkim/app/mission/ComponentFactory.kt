package com.omjoonkim.app.mission

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.app.AppComponentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProviders
import com.omjoonkim.app.mission.di.AppModule
import com.omjoonkim.app.mission.ui.BaseActivity
import com.omjoonkim.app.mission.ui.main.MainActivity
import com.omjoonkim.app.mission.ui.search.SearchActivity
import com.omjoonkim.app.mission.viewmodel.MainViewModelImpl
import com.omjoonkim.app.mission.viewmodel.SearchViewModelImpl
import com.omjoonkim.app.mission.viewmodel.ViewModel

class ComponentFactory : AppComponentFactory() {

    private lateinit var appModule: AppModule
    private lateinit var viewModelFactory: ViewModelFactory

    override fun instantiateApplicationCompat(
        cl: ClassLoader,
        className: String
    ): Application {
        val app = super.instantiateApplicationCompat(cl, className) as App
        appModule = AppModule(app)
        viewModelFactory = ViewModelFactory(appModule)
        return app
    }

    override fun instantiateActivityCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): Activity {
        val activity = super.instantiateActivityCompat(cl, className, intent)
        return if (activity is BaseActivity<*>) {
            when (activity) {
                is MainActivity -> activity.preBindViewModel {
                    ViewModelProviders.of(this, viewModelFactory).get(MainViewModelImpl::class.java)
                }
                is SearchActivity -> activity.preBindViewModel {
                    ViewModelProviders.of(this, viewModelFactory).get(SearchViewModelImpl::class.java)
                }
                else -> throw IllegalArgumentException()
            }
        } else activity
    }

    private fun <T : ViewModel> BaseActivity<T>.preBindViewModel(
        findViewModel: BaseActivity<T>.() -> T
    ) = apply {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                bind(findViewModel())
            }
        })
    }
}
