package com.example.submissionawalnavdanapi.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.data.response.Response
import com.example.submissionawalnavdanapi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    init {
        fetchUpcomingEvents()
        fetchFinishedEvents()
    }

    private fun fetchUpcomingEvents() {
        val client = ApiConfig.getApiService().getUpcomingEvents()
        client.enqueue(object : Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response> // Fix type
            ) {
                if (response.isSuccessful) {
                    _upcomingEvents.value = response.body()?.listEvents // Updated to listEvents
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun fetchFinishedEvents() {
        val client = ApiConfig.getApiService().getFinishedEvents()
        client.enqueue(object : Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response> // Fix type
            ) {
                if (response.isSuccessful) {
                    _finishedEvents.value = response.body()?.listEvents // Updated to listEvents
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}
