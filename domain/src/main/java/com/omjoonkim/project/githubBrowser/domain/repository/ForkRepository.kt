package com.omjoonkim.project.githubBrowser.domain.repository

import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import io.reactivex.Single

interface ForkRepository {
    fun getForks(userName: String, id: String): Single<List<Fork>>
}
