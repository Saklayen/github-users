package com.saklayen.githubusers.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.saklayen.githubusers.R
import com.saklayen.githubusers.base.ui.BaseFragment
import com.saklayen.githubusers.base.utils.launchAndRepeatWithViewLifecycle
import com.saklayen.githubusers.base.utils.navigate
import com.saklayen.githubusers.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.Reword
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    var language = "en"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        mBinding.loading.testRv.apply {
            itemAnimator = null
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.navigateToFollowers.collect {
                    navigate(
                        HomeFragmentDirections.navigateFromHomeFragmentToFollowersFragment(it)
                    )
                }
            }
            launch {
                viewModel.changeLanguage.collect {
                    Timber.d("changeLanguage clicked--------")
                    language =  if(language == "en") "bn" else "en"
                    Restring.locale = Locale(language)
                    Reword.reword(mBinding.root)

                }
            }
        }
    }
}
