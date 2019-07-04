package com.omjoonkim.project.githubBrowser.remote.mapper

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
import com.omjoonkim.project.githubBrowser.remote.model.RepoModel

class ForkEntityMapper(
    private val userMapper: UserEntityMapper
) : EntityMapper<ForkModel, Fork> {

    override fun mapFromRemote(model: ForkModel) = Fork(
        model.name,
        model.fullName,
        userMapper.mapFromRemote(model.owner)
    )
}
