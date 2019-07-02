package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.ui.BaseView

interface RepoDetailView : BaseView{
    fun setName(name : String)
    fun setDescription(description : String)
    fun setStarCount(count : String)
}
