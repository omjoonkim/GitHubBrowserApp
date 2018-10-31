package com.omjoonkim.project.githubBrowser.remote.mapper

interface EntityMapper<in M, out E> {

    fun mapFromRemote(model: M): E

}
