package com.dicoding.submission2.githubuserapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.githubuserapp.datasource.DetailUserResponse
import com.dicoding.submission2.githubuserapp.datasource.ItemsItem
import com.dicoding.submission2.githubuserapp.datasource.SearchResponse
import com.dicoding.submission2.githubuserapp.networking.ApiConfig
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.TAG
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.USERNAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _search = MutableLiveData<List<ItemsItem>>()
    val search: LiveData<List<ItemsItem>> = _search

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser
    private val _followers = MutableLiveData<List<ItemsItem>?>(null)
    val followers: LiveData<List<ItemsItem>?> = _followers
    private val _following = MutableLiveData<List<ItemsItem>?>(null)
    val following: LiveData<List<ItemsItem>?> = _following

    fun findItems(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getSearchData(USERNAME)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUser(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getSearchData(dataUsers)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUser(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getDetailUserData(dataUsers)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowersData(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getFollowersUserData(dataUsers)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowingData(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getFollowingUserData(dataUsers)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}

