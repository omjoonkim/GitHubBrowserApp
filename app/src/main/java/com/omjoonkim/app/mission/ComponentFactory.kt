package com.omjoonkim.app.mission

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.app.AppComponentFactory
import com.omjoonkim.app.mission.di.AppModule
import com.omjoonkim.app.mission.ui.main.MainActivity
import com.omjoonkim.app.mission.ui.search.SearchActivity
import com.omjoonkim.app.mission.viewmodel.MainViewModel
import com.omjoonkim.app.mission.viewmodel.SearchViewModel

class ComponentFactory : AppComponentFactory() {

    private var appModule: AppModule? = null

    override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
        val app = App()
        appModule = AppModule(app)
        return app
    }

    override fun instantiateActivityCompat(cl: ClassLoader, className: String, intent: Intent?): Activity = appModule?.let {
        when (className) {
            MainActivity::class.java.name -> MainActivity(MainViewModel(it.enviorment))
            SearchActivity::class.java.name -> SearchActivity(SearchViewModel(it.enviorment))
            else -> throw IllegalArgumentException()
        }
    } ?: throw IllegalStateException()
}
