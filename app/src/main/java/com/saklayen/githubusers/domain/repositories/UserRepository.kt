package com.saklayen.githubusers.domain.repositories

import com.saklayen.githubusers.domain.Result
import com.saklayen.githubusers.ControlledRunner
import com.saklayen.githubusers.api.ApiService
import com.saklayen.githubusers.api.NetworkResource
import com.saklayen.githubusers.domain.model.User
import com.saklayen.githubusers.hilt.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    val apiService: ApiService
) {
    private val controlledRunner = ControlledRunner<Flow<Result<User>>>()

    suspend fun fetchUser(name: String): Flow<Result<User>> {
        return controlledRunner.cancelPreviousThenRun {
            object : NetworkResource<User>(dispatcher) {
                override suspend fun createCall() = apiService.fetchUser(name)
            }.asFlow()
        }
    }
}
