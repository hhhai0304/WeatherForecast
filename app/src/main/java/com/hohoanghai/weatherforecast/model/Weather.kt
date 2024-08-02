package com.hohoanghai.weatherforecast.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    @Json(name = "app_temp")
    val appTemp: Double,
    val aqi: Long,
    @Json(name = "city_name")
    val cityName: String,
    val clouds: Long,
    @Json(name = "country_code")
    val countryCode: String,
    val datetime: String,
    val dewpt: Long,
    val dhi: Long,
    val dni: Long,
    @Json(name = "elev_angle")
    val elevAngle: Double,
    val ghi: Long,
    @Json(name = "h_angle")
    val hAngle: Long,
    val lat: Double,
    val lon: Double,
    @Json(name = "ob_time")
    val obTime: String,
    val pod: String,
    val precip: Long,
    val pres: Double,
    val rh: Long,
    val slp: Long,
    val snow: Long,
    @Json(name = "solar_rad")
    val solarRad: Long,
    val sources: List<String>,
    @Json(name = "state_code")
    val stateCode: String,
    val station: String,
    val sunrise: String,
    val sunset: String,
    val temp: Long,
    val timezone: String,
    val ts: Long,
    val uv: Long,
    val vis: Long,
    val weather: WeatherData,
    @Json(name = "wind_cdir")
    val windCdir: String,
    @Json(name = "wind_cdir_full")
    val windCdirFull: String,
    @Json(name = "wind_dir")
    val windDir: Long,
    @Json(name = "wind_spd")
    val windSpd: Double,
) : Parcelable