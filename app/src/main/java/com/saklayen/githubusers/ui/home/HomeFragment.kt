package com.saklayen.githubusers.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.saklayen.githubusers.R
import com.saklayen.githubusers.base.ui.BaseFragment
import com.saklayen.githubusers.base.utils.launchAndRepeatWithViewLifecycle
import com.saklayen.githubusers.base.utils.navigate
import com.saklayen.githubusers.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.navigateToFollowers.collect {
                    navigate(
                        HomeFragmentDirections.navigateFromHomeFragmentToFollowersFragment(it)
                    )
                }
            }
        }
    }
}
