package com.mineversal.githubuser.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mineversal.githubuser.api.ApiConfig
import com.mineversal.githubuser.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel: ViewModel() {
    private val _user = MutableLiveData<UserDetailResponse>()
    val user: LiveData<UserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUserDetail()
    }

    fun setUserDetail(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object: Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(null, error, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getUserDetail(): LiveData<UserDetailResponse> {
        return _user
    }

    companion object {
        private const val TAG = "SearchViewModel"
        private const val error = "Data Gagal Dimuat"
    }
}