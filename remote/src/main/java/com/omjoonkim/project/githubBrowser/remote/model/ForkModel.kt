package com.omjoonkim.project.githubBrowser.remote.model

import com.google.gson.annotations.SerializedName

data class ForkModel(
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val owner: UserModel,
    @SerializedName("stargazers_count") val starCount: String
)
