package com.hohoanghai.weatherforecast.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherData(
    val code: Long,
    val description: String,
    val icon: String,
) : Parcelable