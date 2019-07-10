package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import io.reactivex.Single

interface ForkRepository {
    fun gets(userName: String, id: String): Single<List<Fork>>
}
