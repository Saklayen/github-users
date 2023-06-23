package com.saklayen.githubusers.ui.followers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saklayen.githubusers.EMPTY
import com.saklayen.githubusers.WhileViewSubscribed
import com.saklayen.githubusers.domain.model.Follower
import com.saklayen.githubusers.domain.usecases.FollowerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(private val followerListUseCase: FollowerListUseCase) :
    ViewModel() {
    val followerUrl = MutableStateFlow(String.EMPTY)
    val userAvatar = MutableStateFlow(String.EMPTY)
    private val fetchFollowers =
        MutableSharedFlow<String>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val followers = fetchFollowers.flatMapLatest {
        followerListUseCase(it)
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = null
    )

    init {
        viewModelScope.launch {
            followerUrl.collect{
                if( it.isNotEmpty()){
                    fetchFollowers(it)
                }
            }
        }
    }

    private val _navigateToProfile = Channel<Follower>(Channel.CONFLATED)
    val navigateToProfile = _navigateToProfile.receiveAsFlow()

    private fun fetchFollowers(followerUrl: String) {
        fetchFollowers.tryEmit(followerUrl)
    }

    fun navigateToProfile(follower: Follower) {
        _navigateToProfile.trySend(follower)
    }
}