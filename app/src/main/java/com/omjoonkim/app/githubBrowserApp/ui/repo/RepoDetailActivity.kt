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
        initRecyclerView()
        presenter.onCreate(intent.getStringExtra(KEY_USER_NAME), intent.getStringExtra(KEY_REPO_NAME))
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter()
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

    override fun refreshForks(data: List<ForkModel>) {
        (recyclerView.adapter as? Adapter)?.refresh(data)
    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        private val data = mutableListOf<ForkModel>()

        fun refresh(data: List<ForkModel>) {
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
            fun bind(item: ForkModel) {
                binding.data = item
            }
        }
    }
}
