package com.omjoonkim.app.githubBrowserApp.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.omjoonkim.app.githubBrowserApp.R
import com.omjoonkim.app.githubBrowserApp.databinding.ActivitySearchBinding
import com.omjoonkim.app.githubBrowserApp.ui.BaseActivity
import com.omjoonkim.app.githubBrowserApp.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SearchActivity : BaseActivity() {

    private val keyboardController by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
        binding.setLifecycleOwner(this)
        actionbarInit(binding.toolbar, isEnableNavi = false)

        val viewModel = getViewModel<SearchViewModel>()
        binding.viewModel = viewModel

        viewModel.output.goResultActivity()
            .observe {
                keyboardController.hideSoftInputFromWindow(binding.editText.windowToken, 0)
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("githubbrowser://repos/$it")
                    )
                )
            }
    }
}
