package com.omjoonkim.app.mission.di

import com.omjoonkim.app.mission.App
import com.omjoonkim.app.mission.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class))
interface AppComponent {

    @Component.Builder interface Builder {
        @BindsInstance fun application(application: App): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(activity: MainActivity)
}