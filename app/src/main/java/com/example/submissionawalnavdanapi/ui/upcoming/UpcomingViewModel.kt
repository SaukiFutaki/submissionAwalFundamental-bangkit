package com.example.submissionawalnavdanapi.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.data.response.Response
import com.example.submissionawalnavdanapi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback

class UpcomingViewModel : ViewModel() {
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchUpcomingEvents()
    }

    private fun fetchUpcomingEvents() {
        _loading.value = true
        val client = ApiConfig.getApiService().getUpcomingEvents()
        client.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _upcomingEvents.value = response.body()?.listEvents
                } else {
                    _error.value = "Failed to load events"
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                _loading.value = false
                _error.value = t.message
            }
        })
    }
}
