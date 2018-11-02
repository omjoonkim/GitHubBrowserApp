package com.omjoonkim.app.githubBrowserApp

import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.omjoonkim.app.githubBrowserApp.generated.callback.OnClickListener


@BindingAdapter("android:url")
fun ImageView.setImageByGlide(url: String) {
    setImageWithGlide(url)
}
