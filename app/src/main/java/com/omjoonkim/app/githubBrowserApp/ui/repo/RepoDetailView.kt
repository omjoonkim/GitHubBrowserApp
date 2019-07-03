package com.omjoonkim.app.githubBrowserApp.ui.repo

import com.omjoonkim.app.githubBrowserApp.ui.BaseView
import com.omjoonkim.project.githubBrowser.remote.model.ForkModel

interface RepoDetailView : BaseView {
    fun setName(name: String)
    fun setDescription(description: String)
    fun setStarCount(count: String)
    fun refreshForks(data: List<ForkModel>)
}
