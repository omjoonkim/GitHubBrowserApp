package com.omjoonkim.app.mission.di

import com.omjoonkim.app.mission.ui.SearchActivity
import com.omjoonkim.app.mission.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun searchActivity(): SearchActivity
}