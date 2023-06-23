package com.saklayen.githubusers.api

import com.saklayen.githubusers.domain.model.Follower
import com.saklayen.githubusers.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @GET("/users/{name}")
    fun fetchUser(@Path("name") name: String): Flow<ApiResponse<User>>

    @GET
    fun fetchFollowers(@Url url: String): Flow<ApiResponse<List<Follower>>>

}
