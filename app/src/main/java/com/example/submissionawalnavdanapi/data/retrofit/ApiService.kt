package com.example.submissionawalnavdanapi.data.retrofit


import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.data.response.ResponseEvent
import retrofit2.Call
import retrofit2.http.*

interface  ApiService {
//    @GET("events/{id}"
//        fun getEvent(
//            @Path("id") id: String
//        ): Call<Response>
    @GET("events")
    fun getUpcomingEvents(@Query("active") active: Int = 1): Call<ResponseEvent>

    // Fetch finished events
    @GET("events")
    fun getFinishedEvents(@Query("active") active: Int = 0): Call<ResponseEvent>

    // Search events
    @GET("events")
    fun searchEvents(@Query("active") active: Int = -1, @Query("q") keyword: String): Call<ResponseEvent>

    // Fetch event details
    @GET("events/{id}")
    fun getEventDetails(@Path("id") eventId: String): Call<ResponseEvent>
}