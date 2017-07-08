package com.omjoonkim.app.mission.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.omjoonkim.app.mission.R
import kotlinx.android.synthetic.main.activity_search.*
import android.content.Intent
import android.net.Uri


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        button_search.setOnClickListener {
            if (editText.text.isNotEmpty())
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("testapp://repos/${editText.text}")))
        }
    }
}