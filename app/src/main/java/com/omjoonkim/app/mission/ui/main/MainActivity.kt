package com.omjoonkim.app.mission.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omjoonkim.app.mission.R
import com.omjoonkim.app.mission.error.Errors
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import com.omjoonkim.app.mission.setImageWithGlide
import com.omjoonkim.app.mission.showToast
import com.omjoonkim.app.mission.ui.BaseActivity
import com.omjoonkim.app.mission.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.viewholder_user_info.view.*
import kotlinx.android.synthetic.main.viewholder_user_repo.view.*

class MainActivity : BaseActivity<MainViewModel>() {

    private val adapter: MainListAdapter by lazy { MainListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewInit()
    }

    override fun bind(viewModel: MainViewModel) {
        with(viewModel) {
            input.searchedUserName(intent.data.path.substring(1))

            output.actionBarInit()
                .bind {
                    actionbarInit(toolbar, title = it)
                }
            output.loading()
                .bind {
                    if (it)
                        loadingDialog.show()
                    else
                        loadingDialog.dismiss()
                }
            output.refreshListData()
                .bind {
                    adapter.user = it.first
                    adapter.repos = it.second
                    adapter.notifyDataSetChanged()
                }
            output.error()
                .bind {
                    if (it is Errors)
                        showToast(it.errorText)
                    else
                        showToast("알 수 없는 에러.")
                    finish()
                }
        }
    }

    private fun recyclerViewInit() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private inner class MainListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val VIEWTYPE_USER_INFO = 0
        val VIEWTYPE_USER_REPO = 1

        var user: User? = null
        var repos = emptyList<Repo>()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                0 -> UserInfoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_user_info, parent, false))
                1 -> UserRepoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_user_repo, parent, false))
                else -> throw Exception()
            }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is UserRepoViewHolder)
                holder.bind(repos.getOrNull(position - 1))
            else if (holder is UserInfoViewHolder)
                holder.bind(user)
        }

        override fun getItemViewType(position: Int): Int =
            if (position == 0)
                VIEWTYPE_USER_INFO
            else
                VIEWTYPE_USER_REPO

        override fun getItemCount(): Int = repos.size + (user?.let { 1 } ?: 0)

        private inner class UserInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(item: User?) =
                item?.let {
                    itemView.userName.text = it.name
                    itemView.profile.setImageWithGlide(item.profile)
                }

        }

        private inner class UserRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(item: Repo?) =
                item?.let {
                    itemView.repoName.text = it.name
                    itemView.description.text = it.description
                    itemView.countOfStar.text = it.starCount.toString()
                }
        }
    }
}
