package com.omjoonkim.app.mission.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.omjoonkim.app.mission.R
import com.omjoonkim.app.mission.error.Errors
import com.omjoonkim.app.mission.network.model.Repo
import com.omjoonkim.app.mission.network.model.User
import com.omjoonkim.app.mission.setImageWithGlide
import com.omjoonkim.app.mission.showToast
import com.omjoonkim.app.mission.ui.BaseActivity
import com.omjoonkim.app.mission.viewmodel.MainViewModel
import com.omjoonkim.app.mission.viewmodel.RequiresActivityViewModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.viewholder_user_info.view.*
import kotlinx.android.synthetic.main.viewholder_user_repo.view.*
import javax.inject.Inject

@RequiresActivityViewModel(value = MainViewModel::class)
class MainActivity : BaseActivity<MainViewModel>() {

    @Inject
    lateinit var requestManager: RequestManager

    private val adapter: MainListAdapter by lazy { MainListAdapter(requestManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewInit()

        viewModel.outPuts.user()
                .bindToLifecycle(this)
                .subscribe { adapter.user = it }

        viewModel.outPuts.repos()
                .bindToLifecycle(this)
                .subscribe {
                    adapter.repos = it
                    adapter.notifyDataSetChanged()
                }

        viewModel.error.bindToLifecycle(this)
                .subscribe {
                    if (it is Errors)
                        showToast(it.errorText)
                    else
                        showToast("알 수 없는 에러.")
                    finish()
                }
    }

    private fun recyclerViewInit() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private class MainListAdapter(val requestManager: RequestManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        companion object {
            const val VIEWTYPE_USER_INFO = 0
            const val VIEWTYPE_USER_REPO = 1
        }

        var user: User? = null
        var repos = emptyList<Repo>()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
                when (viewType) {
                    0 -> UserInfoViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_user_info, parent, false), requestManager)
                    1 -> UserRepoViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_user_repo, parent, false))
                    else -> throw Exception()
                }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
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

        private class UserInfoViewHolder(view: View, val requestManager: RequestManager) : RecyclerView.ViewHolder(view) {
            fun bind(item: User?) =
                    item?.let {
                        itemView.userName.text = it.name
                        itemView.profile.setImageWithGlide(requestManager, item.profile)
                    }

        }

        private class UserRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(item: Repo?) =
                    item?.let {
                        itemView.repoName.text = it.name
                        itemView.description.text = it.description
                        itemView.countOfStar.text = it.starCount.toString()
                    }
        }
    }
}