package com.saklayen.githubusers.domain.usecases

import com.saklayen.githubusers.domain.FlowUseCase
import com.saklayen.githubusers.domain.Result
import com.saklayen.githubusers.domain.model.Follower
import com.saklayen.githubusers.domain.model.User
import com.saklayen.githubusers.domain.repositories.FollowerListRepository
import com.saklayen.githubusers.hilt.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class FollowerListUseCase @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val repository: FollowerListRepository
) : FlowUseCase<String, List<Follower>>(ioDispatcher) {
    override suspend fun execute(parameters: String): Flow<Result<List<Follower>>> {
        return repository.fetchFollowerList(parameters)
    }
}
