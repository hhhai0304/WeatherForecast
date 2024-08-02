package com.hohoanghai.weatherforecast.network.response

import android.os.Parcelable
import com.hohoanghai.weatherforecast.model.Weather
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherListResponse(
    val count: Int,
    val data: List<Weather>,
) : Parcelable