package com.omjoonkim.project.githubBrowser.domain.exception

class NetworkException(
    message: String,
    val code: Int
) : Throwable(message)
