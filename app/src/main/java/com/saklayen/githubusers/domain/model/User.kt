package com.saklayen.githubusers.domain.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class User(
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    @Json(name = "bio")
    val bio: String?,
    val followers: Int?,
    @Json(name = "followers_url")
    val followersUrl: String?,
    @Json(name = "following")
    val following: Int?,
    @Json(name = "following_url")
    val followingUrl: String?,
    val login: String?,
    @Json(name = "name")
    val name: String,
): Parcelable
