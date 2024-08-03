package com.hohoanghai.weatherforecast.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hohoanghai.weatherforecast.R
import com.hohoanghai.weatherforecast.model.City
import com.hohoanghai.weatherforecast.model.Weather
import com.hohoanghai.weatherforecast.network.failure.EmptyWeatherException
import com.hohoanghai.weatherforecast.repository.CityRepository
import com.hohoanghai.weatherforecast.repository.WeatherRepository
import kotlinx.coroutines.launch

enum class DetailState { Loading, Data, Error }

class DetailViewModel(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _state = MutableLiveData<DetailState>()
    val state: LiveData<DetailState> get() = _state

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> get() = _errorMessage

    private val _city = MutableLiveData<City>()
    val city: LiveData<City> get() = _city

    private val _weather = MutableLiveData<Weather?>()
    val weather: LiveData<Weather?> get() = _weather

    init {
        _state.value = DetailState.Loading
        _city.value = City()
        _errorMessage.value = R.string.unknown_error
    }

    fun getCurrentWeather(city: City? = null) {
        _state.postValue(DetailState.Loading)
        city?.let { _city.postValue(it) }
        viewModelScope.launch {
            val coordinate = (city ?: _city.value!!).coordinate
            val result = weatherRepository.getCurrent(coordinate.latitude, coordinate.longitude)
            if (result.isSuccess) {
                val weather = result.getOrNull()
                _weather.postValue(weather)
                _state.postValue(DetailState.Data)
            } else {
                val errorMessage = when (result.exceptionOrNull()) {
                    is EmptyWeatherException -> R.string.weather_not_found
                    else -> R.string.unknown_error
                }
                _errorMessage.postValue(errorMessage)
                _state.postValue(DetailState.Error)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            cityRepository.toggleFavorite(_city.value!!)
            _city.value = _city.value!!.copy(isFavorite = !_city.value!!.isFavorite)
        }
    }
}