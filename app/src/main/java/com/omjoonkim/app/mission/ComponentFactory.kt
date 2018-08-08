package com.omjoonkim.app.mission

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.app.AppComponentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.omjoonkim.app.mission.di.AppModule
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
        return when (activity) {
            is MainActivity -> activity.apply {
                lifecycle.addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                    fun onCreate() {
                        this@apply.bind(
                            androidx.lifecycle.ViewModelProviders.of(this@apply, viewModelFactory).get(MainViewModelImpl::class.java)
                        )
                    }
                })
            }
            is SearchActivity -> activity.apply {
                lifecycle.addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                    fun onCreate() {
                        this@apply.bind(
                            androidx.lifecycle.ViewModelProviders.of(this@apply, viewModelFactory).get(SearchViewModelImpl::class.java)
                        )
                    }
                })
            }
            else -> throw IllegalArgumentException()
        }
    }

}
