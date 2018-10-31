package com.omjoonkim.app.githubBrowserApp

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun Context.showToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.showToast(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun ImageView.setImageWithGlide( url: String?) =
        url?.let {
            try {
                Glide.with(context).load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .error(R.mipmap.ic_launcher_round)
                        .into(this)
            } catch (ignore: Exception) {
            }
        } ?: setImageResource(0)


fun <T> MutableLiveData<Unit>.call(ignore: T) {
    value = kotlin.Unit
}

fun MutableLiveData<Unit>.call() {
    value = kotlin.Unit
}

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (T?) -> Unit) {
    this.observe(owner, androidx.lifecycle.Observer { observer(it) })
}

inline fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    this.observe(owner, androidx.lifecycle.Observer { observer(it!!) })
}
