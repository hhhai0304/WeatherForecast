package com.hohoanghai.weatherforecast.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    @Json(name = "app_temp")
    val appTemp: Double,
    val aqi: Double,
    @Json(name = "city_name")
    val cityName: String,
    val clouds: Double,
    @Json(name = "country_code")
    val countryCode: String,
    val datetime: String,
    val dewpt: Double,
    val dhi: Double,
    val dni: Double,
    @Json(name = "elev_angle")
    val elevAngle: Double,
    val ghi: Double,
    val lat: Double,
    val lon: Double,
    @Json(name = "ob_time")
    val obTime: String,
    val pod: String,
    val precip: Double,
    val pres: Double,
    val rh: Double,
    val slp: Double,
    val snow: Double,
    @Json(name = "solar_rad")
    val solarRad: Double,
    val sources: List<String>,
    @Json(name = "state_code")
    val stateCode: String,
    val station: String,
    val sunrise: String,
    val sunset: String,
    val temp: Double,
    val timezone: String,
    val ts: Double,
    val uv: Double,
    val vis: Double,
    val weather: WeatherData,
    @Json(name = "wind_cdir")
    val windCdir: String,
    @Json(name = "wind_cdir_full")
    val windCdirFull: String,
    @Json(name = "wind_dir")
    val windDir: Double,
    @Json(name = "wind_spd")
    val windSpd: Double,
) : Parcelable