package com.omjoonkim.app.GitHubBrowserApp

import android.app.Activity
import android.content.Intent
import androidx.core.app.AppComponentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.omjoonkim.app.GitHubBrowserApp.ui.BaseActivity
import com.omjoonkim.app.GitHubBrowserApp.ui.main.MainActivity
import com.omjoonkim.app.GitHubBrowserApp.ui.search.SearchActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ComponentFactory : AppComponentFactory() {

    override fun instantiateActivityCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): Activity {
        val activity = super.instantiateActivityCompat(cl, className, intent)
        return if (activity is BaseActivity<*>) {
            when (activity) {
                is MainActivity -> activity.preBindViewModel {
                    activity.bind(activity.getViewModel())
                }
                is SearchActivity -> activity.preBindViewModel {
                    activity.bind(activity.getViewModel())
                }
                else -> throw IllegalArgumentException()
            }
        } else activity
    }

    private fun <T : ViewModel> BaseActivity<T>.preBindViewModel(
        bindViewModel: () -> Unit
    ) = apply {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                bindViewModel.invoke()
            }
        })
    }
}
