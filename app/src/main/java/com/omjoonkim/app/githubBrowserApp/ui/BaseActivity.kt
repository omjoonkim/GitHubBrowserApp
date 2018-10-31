package com.omjoonkim.app.githubBrowserApp.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import com.omjoonkim.app.githubBrowserApp.R
import com.omjoonkim.app.githubBrowserApp.observeNotNull

abstract class BaseActivity : AppCompatActivity() {

    protected fun actionbarInit(toolbar: Toolbar, titleColor: Int = Color.WHITE, isEnableNavi: Boolean = true, onClickHomeButton: () -> Unit = {}) {
        toolbar.setTitleTextColor(titleColor)
        setSupportActionBar(toolbar)
        if (isEnableNavi) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                onClickHomeButton.invoke()
            }
        }
    }

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) = observeNotNull(this@BaseActivity, observer)
}
