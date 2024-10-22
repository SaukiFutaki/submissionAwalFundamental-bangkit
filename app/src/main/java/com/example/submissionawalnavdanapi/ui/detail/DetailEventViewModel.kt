package com.example.submissionawalnavdanapi.ui.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.data.response.ResponseEvent
import com.example.submissionawalnavdanapi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel : ViewModel() {




    // LiveData for holding the selected event details
    private val _eventDetails = MutableLiveData<List<ListEventsItem>>()
    val eventDetails: LiveData<List<ListEventsItem>> = _eventDetails

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {

        fetchEventDetails()
    }

    private fun fetchEventDetails() {
        _loading.value = true
        val client = ApiConfig.getApiService().getEventDetails("1")
        client.enqueue(object : Callback<ResponseEvent> {
            override fun onResponse(call: Call<ResponseEvent>, response: Response<ResponseEvent>) {
                if (response.isSuccessful) {
                    _eventDetails.value = response.body()?.listEvents
                } else {
                    _error.value = "Failed to load events"
                }
            }

            override fun onFailure(call: Call<ResponseEvent>, t: Throwable) {
                _error.value = t.message
            }
        })
    }
}