package com.omjoonkim.app.githubBrowserApp

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:url")
fun ImageView.setImageByGlide(url: String) {
    setImageWithGlide(url)
}
