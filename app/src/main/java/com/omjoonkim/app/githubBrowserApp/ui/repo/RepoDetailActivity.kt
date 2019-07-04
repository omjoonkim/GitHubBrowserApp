package com.omjoonkim.app.githubBrowserApp.ui.repo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omjoonkim.app.githubBrowserApp.R
import com.omjoonkim.app.githubBrowserApp.databinding.ViewholderRepoForkBinding
import com.omjoonkim.app.githubBrowserApp.databinding.ViewholderUserInfoBinding
import com.omjoonkim.app.githubBrowserApp.ui.BaseActivity
import com.omjoonkim.project.githubBrowser.domain.entity.Fork
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.remote.model.ForkModel
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
        actionbarInit(toolbar, onClickHomeButton = {
            onBackPressed()
        })
        initRecyclerView()
        presenter.onCreate(
            intent.getStringExtra(KEY_USER_NAME),
            intent.getStringExtra(KEY_REPO_NAME)
        )
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter()
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
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

    override fun refreshForks(data: List<Fork>) {
        (recyclerView.adapter as? Adapter)?.refresh(data)
    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        private val data = mutableListOf<Fork>()

        fun refresh(data: List<Fork>) {
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ViewholderRepoForkBinding.inflate(LayoutInflater.from(parent.context)))

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(data[position])
        }

        private inner class ViewHolder(
            private val binding: ViewholderRepoForkBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Fork) {
                binding.data = item
            }
        }
    }
}
