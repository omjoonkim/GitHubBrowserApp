package com.omjoonkim.app.githubBrowserApp

import android.util.Log

class Logger {

    fun d(t: Throwable) {
        t.printStackTrace()
        Log.e("e",t.toString())
    }
}
