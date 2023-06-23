package com.saklayen.githubusers.ui

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.saklayen.githubusers.R
import com.saklayen.githubusers.base.ui.BaseActivity
import com.saklayen.githubusers.base.utils.findNavControllerByFragmentContainerView
import com.saklayen.githubusers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mNavController by lazy { findNavControllerByFragmentContainerView(R.id.nav_host) }

    @Inject
    lateinit var connectivityManager: ConnectivityManager
    private var isInternetLost = false

    private val networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (isInternetLost) {
                isInternetLost = false
                Snackbar.make(binding.root, "Internet connected !", Snackbar.LENGTH_SHORT).show()
            }
        }

        override fun onUnavailable() {
            super.onUnavailable()
            isInternetLost = true
            Snackbar.make(binding.root, "No Internet!", Snackbar.LENGTH_INDEFINITE).show()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isInternetLost = true
            Snackbar.make(binding.root, "Internet disconnected !", Snackbar.LENGTH_INDEFINITE)
                .show()
        }

    }

    companion object {
        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.homeFragment,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS)
        toolbar.setupWithNavController(mNavController, appBarConfiguration)
    }
}
