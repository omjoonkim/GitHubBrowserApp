package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.ui.BaseView
import com.omjoonkim.project.githubBrowser.domain.entity.Fork

interface RepoDetailView : BaseView {
    fun setName(name: String)
    fun setDescription(description: String)
    fun setStarCount(count: String)
    fun refreshForks(data: List<Fork>)
    fun setToolbarTitle(title: String)
}
