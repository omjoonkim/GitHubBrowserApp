package com.omjoonkim.app.GitHubBrowserApp.ui

import android.app.ProgressDialog
import android.graphics.Color
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.omjoonkim.app.GitHubBrowserApp.R
import com.omjoonkim.app.GitHubBrowserApp.rx.Parameter
import com.omjoonkim.app.GitHubBrowserApp.rx.bind
import com.omjoonkim.app.GitHubBrowserApp.viewmodel.ViewModel
import io.reactivex.*
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity<T> : AppCompatActivity() where T : ViewModel {

    protected val backPress: PublishSubject<Parameter> by lazy { PublishSubject.create<Parameter>() }
    protected val clickOptionItems: PublishSubject<MenuItem> by lazy { PublishSubject.create<MenuItem>() }
    protected val loadingDialog by lazy { ProgressDialog(this).apply { setCancelable(false) } }

    abstract fun bind(viewModel: T)

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

    fun <T> Observable<T>.bind(observer: (T) -> Unit) = bind(this@BaseActivity, observer)
    fun <T> Flowable<T>.bind(observer: (T) -> Unit) = bind(this@BaseActivity, observer)
    fun <T> Single<T>.bind(observer: (T) -> Unit) = bind(this@BaseActivity, observer)
    fun <T> Maybe<T>.bind(observer: (T) -> Unit) = bind(this@BaseActivity, observer)
    fun <T> Completable.bind(observer: (T) -> Unit) = bind(this@BaseActivity, observer)
}
