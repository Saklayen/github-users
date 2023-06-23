package com.saklayen.githubusers.base.ui

import androidx.appcompat.widget.Toolbar

interface NavigationHost {
    /** Called by MainNavigationFragment to setup it's toolbar with the navigation controller. */
    fun registerToolbarWithNavigation(toolbar: Toolbar)

    fun activityScreenSwitcher(): ActivityScreenSwitcher
}
