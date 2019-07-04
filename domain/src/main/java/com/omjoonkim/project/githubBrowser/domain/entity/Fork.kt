package com.omjoonkim.project.githubBrowser.domain.entity


data class Fork(
    val name: String,
    val fullName: String,
    val owner: User
)
