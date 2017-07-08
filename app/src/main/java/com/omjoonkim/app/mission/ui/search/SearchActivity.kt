package com.omjoonkim.app.mission.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import com.omjoonkim.app.mission.R
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {
    val keyboardController by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                button_search.isEnabled = s?.isNotEmpty() ?: false
            }
        })
        button_search.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("testapp://repos/${editText.text}")))
            keyboardController?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}