package com.hohoanghai.weatherforecast.repository

import com.hohoanghai.weatherforecast.model.Weather
import com.hohoanghai.weatherforecast.network.WeatherService
import com.hohoanghai.weatherforecast.network.failure.EmptyWeatherException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

interface WeatherRepository {
    suspend fun getCurrent(latitude: Double, longitude: Double): Result<Weather>
}

class WeatherRepositoryImpl(private val weatherService: WeatherService) : WeatherRepository {
    override suspend fun getCurrent(latitude: Double, longitude: Double): Result<Weather> {
        return withContext(Dispatchers.IO) {
            try {
                val result = weatherService.getCurrent(latitude, longitude)
                if (result.data.isNotEmpty()) {
                    return@withContext Result.success(result.data.first())
                }
            } catch (e: Exception) {
                Timber.e(e)
                return@withContext Result.failure<Weather>(e)
            }
            Timber.e("Null weather")
            return@withContext Result.failure(EmptyWeatherException())
        }
    }
}