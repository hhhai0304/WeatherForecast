package com.hohoanghai.weatherforecast.network

import com.hohoanghai.weatherforecast.network.response.WeatherListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current")
    suspend fun getCurrent(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherListResponse
}