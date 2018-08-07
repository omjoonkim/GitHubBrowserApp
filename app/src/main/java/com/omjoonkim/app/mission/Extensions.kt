package com.omjoonkim.app.mission

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
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
