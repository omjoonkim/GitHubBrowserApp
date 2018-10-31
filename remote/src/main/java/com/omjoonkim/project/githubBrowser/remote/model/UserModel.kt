package com.omjoonkim.project.githubBrowser.remote.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val profile: String
)
