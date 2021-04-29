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
import com.omjoonkim.app.githubBrowserApp.viewmodel.SearchViewModelImpl
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SearchActivity : BaseActivity() {

    private val keyboardController by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
        binding.lifecycleOwner = this
        actionbarInit(binding.toolbar, isEnableNavi = false)

        val viewModel: SearchViewModel = getViewModel<SearchViewModelImpl>()
        binding.viewModel = viewModel

        viewModel.goResultActivity()
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
