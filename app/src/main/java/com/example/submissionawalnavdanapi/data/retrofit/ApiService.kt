package com.example.submissionawalnavdanapi.data.retrofit


import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.data.response.Response
import retrofit2.Call
import retrofit2.http.*

interface  ApiService {
//    @GET("events/{id}"
//        fun getEvent(
//            @Path("id") id: String
//        ): Call<Response>
    @GET("events")
    fun getUpcomingEvents(@Query("active") active: Int = 1): Call<Response>

    // Fetch finished events
    @GET("events")
    fun getFinishedEvents(@Query("active") active: Int = 0): Call<Response>

    // Search events
    @GET("events")
    fun searchEvents(@Query("active") active: Int = -1, @Query("q") keyword: String): Call<Response>

    // Fetch event details
    @GET("events/{id}")
    fun getEventDetails(@Path("id") eventId: String): Call<Response>
}