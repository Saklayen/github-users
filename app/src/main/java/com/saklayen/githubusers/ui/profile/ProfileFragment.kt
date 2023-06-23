package com.saklayen.githubusers.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.saklayen.githubusers.R
import com.saklayen.githubusers.base.ui.BaseFragment
import com.saklayen.githubusers.base.utils.launchAndRepeatWithViewLifecycle
import com.saklayen.githubusers.base.utils.navigate
import com.saklayen.githubusers.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by activityViewModels()
    private val navArgs by navArgs<ProfileFragmentArgs>()
    override val haveToolbar = true
    override val resToolbarId = R.id.toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        launchAndRepeatWithViewLifecycle {
            launch {
                navArgs.let {
                    viewModel.setUserProfile(it.userData)
                }
            }
            launch {
                viewModel.navigateToFollowers.collect {
                    navigate(
                        ProfileFragmentDirections.navigateFromProfileFragmentToFollowersFragment(
                            it
                        )
                    )
                }
            }
        }
    }
}
