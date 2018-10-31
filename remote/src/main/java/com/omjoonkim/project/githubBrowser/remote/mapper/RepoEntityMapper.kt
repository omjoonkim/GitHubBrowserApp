package com.omjoonkim.project.githubBrowser.remote.mapper

import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel

class RepoEntityMapper : EntityMapper<RepoModel, Repo> {

    override fun mapFromRemote(model: RepoModel) = Repo(
        model.name,
        model.description ?: "",
        model.starCount
    )
}
