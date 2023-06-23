package com.saklayen.githubusers.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saklayen.githubusers.WhileViewSubscribed
import com.saklayen.githubusers.domain.model.Follower
import com.saklayen.githubusers.domain.model.User
import com.saklayen.githubusers.domain.usecases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProfileViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    private var _userProfile =
        MutableSharedFlow<Follower>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val userProfile = _userProfile.flatMapLatest {
        userUseCase(it.login.toString())
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = null
    )

    private val _navigateToFollowers = Channel<User>(Channel.CONFLATED)
    val navigateToFollowers = _navigateToFollowers.receiveAsFlow()

    fun setUserProfile(follower: Follower) {
        _userProfile.tryEmit(follower)
    }

    fun navigateToFollowers() {
        _navigateToFollowers.trySend(userProfile.value?.data!!)
    }
}
