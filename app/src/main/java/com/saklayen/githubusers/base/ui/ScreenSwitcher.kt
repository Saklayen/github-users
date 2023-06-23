package com.saklayen.githubusers.base.ui

interface ScreenSwitcher<S : Screen> {
    fun open(mScreen: S)
    fun goBack()
}
