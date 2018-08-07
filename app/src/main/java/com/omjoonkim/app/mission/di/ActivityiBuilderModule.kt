package com.omjoonkim.app.mission.di

import com.omjoonkim.app.mission.ui.main.MainActivity
import com.omjoonkim.app.mission.ui.search.SearchActivity

abstract class ActivityBuilderModule {

    abstract fun mainActivity(): MainActivity

    abstract fun searchActivity(): SearchActivity
}
