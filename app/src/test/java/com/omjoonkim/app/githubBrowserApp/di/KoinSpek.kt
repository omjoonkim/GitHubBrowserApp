package com.omjoonkim.app.githubBrowserApp.di

import org.koin.test.KoinTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Root

class KoinRoot(val root: Root) : KoinTest, Root by root
abstract class KoinSpek(koinSpec: KoinRoot.() -> Unit) : Spek({
    koinSpec.invoke(KoinRoot(this))
})
