package com.omjoonkim.app.mission

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.setImageWithGlide(requestManager: RequestManager, url: String?) =
        url?.let {
            try {
                requestManager.load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .error(R.mipmap.ic_launcher_round)
                        .into(this)
            } catch (ignore: Exception) {
            }
        } ?: setImageResource(0)


