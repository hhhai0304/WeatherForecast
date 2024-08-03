package com.hohoanghai.weatherforecast.repository

import android.content.Context
import com.hohoanghai.weatherforecast.database.dao.CityDao
import com.hohoanghai.weatherforecast.model.City
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

interface CityRepository {
    suspend fun initCities(context: Context)
    suspend fun getFavoriteCitiesFlow(): Flow<List<City>>
    suspend fun search(searchKeyword: String): List<City>
    suspend fun toggleFavorite(city: City)
}

class CityRepositoryImpl(private val cityDao: CityDao) : CityRepository {
    override suspend fun initCities(context: Context) {
        withContext(Dispatchers.IO) {
            val tableIsEmpty = cityDao.isEmpty()
            if (tableIsEmpty) {
                val cities = readCitiesFromJson(context)?.map { it.toEntity() }
                if (!cities.isNullOrEmpty()) {
                    cityDao.insertAll(cities)
                }
            }
        }
    }

    private fun readCitiesFromJson(context: Context): List<City>? {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listType = Types.newParameterizedType(List::class.java, City::class.java)
        val jsonAdapter = moshi.adapter<List<City>>(listType)

        context.assets.open("city.json").use { inputStream ->
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            return jsonAdapter.fromJson(bufferedReader.readText())
        }
    }

    override suspend fun getFavoriteCitiesFlow(): Flow<List<City>> {
        return cityDao.selectFavorites().asFlow().map { results -> results.list.map { City(it) } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun search(searchKeyword: String): List<City> {
        return withContext(Dispatchers.IO) {
            return@withContext cityDao.selectByName(searchKeyword, 15).map { City(it) }
        }
    }

    override suspend fun toggleFavorite(city: City) {
        withContext(Dispatchers.IO) {
            cityDao.toggleFavorite(city.id)
        }
    }
}