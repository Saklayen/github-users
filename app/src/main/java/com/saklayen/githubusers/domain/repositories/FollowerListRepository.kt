package com.saklayen.githubusers.domain.repositories

import com.saklayen.githubusers.ControlledRunner
import com.saklayen.githubusers.api.ApiService
import com.saklayen.githubusers.api.NetworkResource
import com.saklayen.githubusers.domain.Result
import com.saklayen.githubusers.domain.model.Follower
import com.saklayen.githubusers.hilt.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowerListRepository @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    val apiService: ApiService
) {
    private val controlledRunner = ControlledRunner<Flow<Result<List<Follower>>>>()

    suspend fun fetchFollowerList(url: String): Flow<Result<List<Follower>>> {
        return controlledRunner.joinPreviousOrRun {
            object : NetworkResource<List<Follower>>(dispatcher) {
                override suspend fun createCall() = apiService.fetchFollowers(url)
            }.asFlow()
        }
    }
}
