package com.omjoonkim.app.mission.ui

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.omjoonkim.app.mission.viewmodel.BaseViewModel
import com.omjoonkim.app.mission.viewmodel.RequiresActivityViewModel
import com.omjoonkim.app.mission.R
import com.omjoonkim.app.mission.rx.Parameter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.subjects.PublishSubject

open class BaseActivity<out T> : RxAppCompatActivity() where T : BaseViewModel {

    protected val backPress: PublishSubject<Parameter> by lazy { PublishSubject.create<Parameter>() }
    protected val clickOptionItems: PublishSubject<MenuItem> by lazy { PublishSubject.create<MenuItem>() }
    protected val loadingDialog by lazy { ProgressDialog(this).apply { setCancelable(false) } }
    private var originalViewModel: T? = null

    @Suppress("UNCHECKED_CAST")
    private fun createViewModel(): T = javaClass.getAnnotation(RequiresActivityViewModel::class.java)?.let {
        val className = it.value.java
        val constructor = className.getConstructor(Context::class.java)
        constructor.newInstance(this) as? T ?: throw RuntimeException()
    } ?: throw RuntimeException()

    protected val viewModel: T
        get() = originalViewModel ?: createViewModel().apply { this@BaseActivity.originalViewModel = this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(this, savedInstanceState)
        viewModel.intent(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        originalViewModel = null
    }


    protected fun actionbarInit(toolbar: Toolbar, titleColor: Int = Color.WHITE, isEnableNavi: Boolean = true, title: String = "") {
        toolbar.title = title
        toolbar.setTitleTextColor(titleColor)

        setSupportActionBar(toolbar)
        if (isEnableNavi)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.home || item?.itemId == android.R.id.home)
            onBackPressed()
        else if (item != null)
            clickOptionItems.onNext(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backPress.onNext(Parameter.EVENT)
    }
}
