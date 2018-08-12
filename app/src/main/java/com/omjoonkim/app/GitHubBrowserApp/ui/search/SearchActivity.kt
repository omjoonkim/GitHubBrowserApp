package com.omjoonkim.app.GitHubBrowserApp.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import com.omjoonkim.app.GitHubBrowserApp.R
import com.omjoonkim.app.GitHubBrowserApp.rx.Parameter
import com.omjoonkim.app.GitHubBrowserApp.ui.BaseActivity
import com.omjoonkim.app.GitHubBrowserApp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<SearchViewModel>() {

    private val keyboardController by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        actionbarInit(toolbar, title = "Search", isEnableNavi = false)
    }

    override fun bind(viewModel: SearchViewModel) {
        with(viewModel) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    input.name(s.toString())
                }
            })
            button_search.setOnClickListener {
                input.clickSearchButton(Parameter.CLICK)
            }
            output.setEnabledSearchButton()
                .bind {
                    button_search.isEnabled = it
                }
            output.goResultActivity()
                .bind {
                    keyboardController?.hideSoftInputFromWindow(editText.windowToken, 0)
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("testapp://repos/$it")))
                }
        }
    }
}
