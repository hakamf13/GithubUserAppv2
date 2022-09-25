package com.dicoding.submission2.githubuserapp.networking

import com.dicoding.submission2.githubuserapp.datasource.DetailUserResponse
import com.dicoding.submission2.githubuserapp.datasource.ItemsItem
import com.dicoding.submission2.githubuserapp.datasource.SearchResponse
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.TOKEN
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    fun getSearchData(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUserData(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUserData(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowingUserData(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}