package com.saklayen.githubusers.base.ui

import androidx.core.app.ActivityCompat
import com.saklayen.githubusers.R

class ActivityScreenSwitcher : ScreenSwitcher<ActivityScreen> {
    private var mActivity: BaseActivity<*>? = null

    fun attach(mActivity: BaseActivity<*>) {
        this.mActivity = mActivity
    }

    fun detach() {
        this.mActivity = null
    }

    override fun open(mScreen: ActivityScreen) {
        when (mActivity) {
            null -> {

                return
            }
            else -> {
                try {
                    val intent = mScreen.intent(mActivity!!)
                    ActivityCompat.startActivity(
                        mActivity!!, intent, mScreen.activityOptions(mActivity!!)
                    )
                    mActivity?.overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
                } catch (e: Exception) {

                }

            }
        }
    }

    override fun goBack() {
        if (mActivity != null) {
            mActivity!!.onBackPressed()
        }
    }
}
