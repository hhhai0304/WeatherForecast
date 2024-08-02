package com.hohoanghai.weatherforecast.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hohoanghai.weatherforecast.model.City
import com.hohoanghai.weatherforecast.repository.CityRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val cityRepository: CityRepository) : ViewModel() {
    fun toggleFavorite(city: City) {
        viewModelScope.launch {
            cityRepository.toggleFavorite(city)
        }
    }
}