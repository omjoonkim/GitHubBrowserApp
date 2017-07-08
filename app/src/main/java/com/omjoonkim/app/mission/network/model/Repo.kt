package com.omjoonkim.app.mission.network.model

import com.google.gson.annotations.SerializedName

data class Repo(val name: String, val description: String, @SerializedName("stargazers_count") val starCount: Int)