package com.saklayen.githubusers.domain.usecases

import com.saklayen.githubusers.domain.FlowUseCase
import com.saklayen.githubusers.domain.model.User
import com.saklayen.githubusers.domain.Result
import com.saklayen.githubusers.domain.repositories.UserRepository
import com.saklayen.githubusers.hilt.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class UserUseCase @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) : FlowUseCase<String, User>(ioDispatcher) {
    override suspend fun execute(parameters: String): Flow<Result<User>> {
        return repository.fetchUser(parameters)
    }
}
