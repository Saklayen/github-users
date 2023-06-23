package com.saklayen.githubusers.ui.followers

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.saklayen.githubusers.R
import com.saklayen.githubusers.base.ui.BaseFragment
import com.saklayen.githubusers.base.utils.launchAndRepeatWithViewLifecycle
import com.saklayen.githubusers.base.utils.navigate
import com.saklayen.githubusers.databinding.FragmentFollowersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowersFragment : BaseFragment<FragmentFollowersBinding>(R.layout.fragment_followers) {
    private val viewModel: FollowersViewModel by viewModels()
    private val navArgs by navArgs<FollowersFragmentArgs>()
    override val haveToolbar = true
    override val resToolbarId = R.id.toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        launchAndRepeatWithViewLifecycle {
            launch {
                navArgs.let {
                    viewModel.followerUrl.value = it.userData.followersUrl.toString()
                    viewModel.userAvatar.value = it.userData.avatarUrl.toString()
                }
            }

            launch {
                viewModel.navigateToProfile.collect {
                    navigate(
                        FollowersFragmentDirections.navigateFromFollowersFragmentToProfileFragment(
                            it
                        )
                    )
                }
            }
        }
    }
}