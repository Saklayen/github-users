package com.saklayen.githubusers.ui.home

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saklayen.githubusers.WhileViewSubscribed
import com.saklayen.githubusers.domain.model.User
import com.saklayen.githubusers.domain.usecases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase) :
    ViewModel() {

    private val _navigateToFollowers = Channel<User>(Channel.CONFLATED)
    val navigateToFollowers = _navigateToFollowers.receiveAsFlow()

    val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            fetchUser(newText)
            return true
        }

        override fun onQueryTextSubmit(newQuery: String): Boolean {
            fetchUser(newQuery)
            return true
        }
    }

    private val fetchUser =
        MutableSharedFlow<String>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val userInfo = fetchUser.flatMapLatest {
        userUseCase(it)
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = null
    )


    fun fetchUser(name: String) {
        fetchUser.tryEmit(name)
    }

    fun navigateToFollowers() {
        _navigateToFollowers.trySend(userInfo.value?.data!!)
    }
}
