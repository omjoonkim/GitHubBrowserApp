package com.omjoonkim.project.githubBrowser.remote.model

import com.google.gson.annotations.SerializedName

data class RepoModel(
    val name: String,
    val description: String?,
    @SerializedName("stargazers_count") val starCount: String
)
