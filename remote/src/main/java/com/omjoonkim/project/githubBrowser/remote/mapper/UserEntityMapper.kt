package com.omjoonkim.project.githubBrowser.remote.mapper

import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.remote.model.UserModel

class UserEntityMapper : EntityMapper<UserModel, User> {

    override fun mapFromRemote(model: UserModel) = User(model.name, model.profile)

}
