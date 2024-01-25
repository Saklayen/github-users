package com.saklayen.githubusers.base.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dev.b3nedikt.restring.Restring
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper

import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding> constructor(@LayoutRes private val mContentLayoutId: Int) :
    AppCompatActivity(),
    NavigationHost {

    @Inject
    lateinit var activityScreenSwitcher: ActivityScreenSwitcher

    protected val binding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView<T>(this, mContentLayoutId)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context))
    }

    private val appCompatDelegate: AppCompatDelegate by lazy {
        ViewPumpAppCompatDelegate(
            baseDelegate = super.getDelegate(),
            baseContext = this,
            wrapContext = Restring::wrapContext
        )
    }

    override fun getDelegate(): AppCompatDelegate {
        return appCompatDelegate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root.filterTouchesWhenObscured = true
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        activityScreenSwitcher.attach(this)
        super.onResume()
    }

    override fun onPause() {
        activityScreenSwitcher.detach()
        super.onPause()
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        //
    }

    protected fun onArrowClick() = activityScreenSwitcher.goBack()

    override fun activityScreenSwitcher() = activityScreenSwitcher
}

