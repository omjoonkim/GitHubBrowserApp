package com.omjoonkim.app.mission.ui

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.omjoonkim.app.mission.R
import com.omjoonkim.app.mission.rx.Parameter
import com.omjoonkim.app.mission.viewmodel.BaseViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity<out T>(protected val viewModel: T) : RxAppCompatActivity() where T : BaseViewModel {

    protected val backPress: PublishSubject<Parameter> by lazy { PublishSubject.create<Parameter>() }
    protected val clickOptionItems: PublishSubject<MenuItem> by lazy { PublishSubject.create<MenuItem>() }
    protected val loadingDialog by lazy { ProgressDialog(this).apply { setCancelable(false) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(this, savedInstanceState)
        viewModel.intent(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
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
