package com.omjoonkim.app.githubBrowserApp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omjoonkim.app.githubBrowserApp.databinding.ActivityMainBinding
import com.omjoonkim.app.githubBrowserApp.databinding.ViewholderUserInfoBinding
import com.omjoonkim.app.githubBrowserApp.databinding.ViewholderUserRepoBinding
import com.omjoonkim.app.githubBrowserApp.showToast
import com.omjoonkim.app.githubBrowserApp.ui.BaseActivity
import com.omjoonkim.app.githubBrowserApp.viewmodel.MainViewModel
import com.omjoonkim.project.githubBrowser.domain.entity.Repo
import com.omjoonkim.app.githubBrowserApp.R
import com.omjoonkim.app.githubBrowserApp.ui.repo.RepoDetailActivity
import com.omjoonkim.app.githubBrowserApp.viewmodel.MainViewModelImpl
import com.omjoonkim.project.githubBrowser.domain.entity.User
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val viewModel: MainViewModel = getViewModel<MainViewModelImpl> {
            parametersOf(intent.data.path.substring(1))
        }
        binding.viewModel = viewModel

        actionbarInit(binding.toolbar, onClickHomeButton = {
            viewModel.clickHomeButton()
        })

        with(viewModel) {
            state().observe { state ->
                if (state.user != null && state.repos.isNotEmpty()) {
                    binding.recyclerView.adapter = MainListAdapter(
                            state.user,
                            state.repos,
                            ::clickUser,
                            ::clickRepo
                    )
                }
            }
            showErrorToast().observe { showToast(it) }
            goProfileActivity().observe {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("githubbrowser://repos/$it")
                        )
                )
            }
            goRepoDetailActivity().observe {
                RepoDetailActivity.start(this@MainActivity, it.first, it.second)
            }
            finish().observe {
                onBackPressed()
            }
        }
    }

    private inner class MainListAdapter(
            val user: User,
            val repos: List<Repo>,
            val onClickUser: (User) -> Unit,
            val onClickRepo: (User, Repo) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val VIEWTYPE_USER_INFO = 0
        private val VIEWTYPE_USER_REPO = 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
                when (viewType) {
                    0 -> UserInfoViewHolder(ViewholderUserInfoBinding.inflate(layoutInflater)).apply {
                        itemView.setOnClickListener {
                            onClickUser.invoke(user)
                        }
                    }
                    1 -> UserRepoViewHolder(ViewholderUserRepoBinding.inflate(layoutInflater)).apply {
                        itemView.setOnClickListener {
                            onClickRepo.invoke(user, repos[adapterPosition - 1])
                        }
                    }
                    else -> throw IllegalStateException()
                }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is UserRepoViewHolder -> holder.bind(repos[position - 1])
                is UserInfoViewHolder -> holder.bind(user)
            }
        }

        override fun getItemViewType(position: Int): Int =
                if (position == 0)
                    VIEWTYPE_USER_INFO
                else
                    VIEWTYPE_USER_REPO

        override fun getItemCount(): Int = repos.size + 1

        private inner class UserInfoViewHolder(
                private val binding: ViewholderUserInfoBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: User) {
                binding.data = item
            }
        }

        private inner class UserRepoViewHolder(
                private val binding: ViewholderUserRepoBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Repo) {
                binding.data = item
            }
        }
    }
}
