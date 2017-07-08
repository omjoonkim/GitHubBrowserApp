package com.omjoonkim.app.mission.ui.main

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.omjoonkim.app.mission.R
import com.omjoonkim.app.mission.ui.BaseActivity
import com.omjoonkim.app.mission.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    private class MainListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private class UserInfoViewHolder(view: View) : RecyclerView.ViewHolder(view)

        private class UserRepoViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}