package com.saklayen.githubusers.ui.home

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saklayen.githubusers.WhileViewSubscribed
import com.saklayen.githubusers.domain.model.User
import com.saklayen.githubusers.domain.usecases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase) :
    ViewModel() {

    val sf = MutableSharedFlow<String>()
    val stF = sf.asS

    private val _navigateToFollowers = Channel<User>(Channel.CONFLATED)
    val navigateToFollowers = _navigateToFollowers.receiveAsFlow()

    private val _changeLanguage = Channel<Unit>(Channel.CONFLATED)
    val changeLanguage = _changeLanguage.receiveAsFlow()

    val dataList = MutableStateFlow(listOf("x", "y"))

    val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            Timber.e("onQueryTextChange $newText")
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

    init {

        viewModelScope.launch {
            stF.collect {
                Timber.d("stF = $it")
            }
        }
        viewModelScope.launch {
            for (i in 1 .. 100){
                delay(5000)
                dataList.value = listOf("$i-x", "$i-y")
            }
        }
        viewModelScope.launch {
            userInfo.collect {
                Timber.e("Response: ${it?.status}")
                Timber.e("Response: ${it?.message}")
            }
        }
    }


    fun fetchUser(name: String) {
        Timber.e("fetchUser $name")
        fetchUser.tryEmit(name)
    }

    fun changeLanguage(){
        _changeLanguage.trySend(Unit)
    }

    fun navigateToFollowers() {
        _navigateToFollowers.trySend(userInfo.value?.data!!)
    }
}
