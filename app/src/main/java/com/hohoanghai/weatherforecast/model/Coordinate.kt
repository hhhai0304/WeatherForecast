package com.hohoanghai.weatherforecast.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinate(
    @Json(name = "lat") val latitude: Double = 0.0,
    @Json(name = "lon") val longitude: Double = 0.0
) : Parcelable
