package com.omjoonkim.app.githubBrowserApp.ui.repo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.omjoonkim.app.githubBrowserApp.R
import com.omjoonkim.app.githubBrowserApp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_repo.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RepoDetailActivity : BaseActivity(), RepoDetailView {

    companion object {
        val KEY_USER_NAME = "userNameKey"
        val KEY_REPO_NAME = "repoNameKey"

        fun start(context: Context, userName: String, repoName: String) {
            context.startActivity(
                Intent(context, RepoDetailActivity::class.java)
                    .putExtra(KEY_USER_NAME, userName)
                    .putExtra(KEY_REPO_NAME, repoName)
            )
        }
    }

    private val presenter: RepoDetailPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        presenter.onCreate(intent.getStringExtra(KEY_USER_NAME), intent.getStringExtra(KEY_REPO_NAME))
    }

    override fun setName(name: String) {
        this.name.text = name
    }

    override fun setDescription(description: String) {
        this.description.text = description
    }

    override fun setStarCount(count: String) {
        this.starCount.text = count
    }
}
