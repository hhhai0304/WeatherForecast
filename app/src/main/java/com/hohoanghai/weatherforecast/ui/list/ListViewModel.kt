package com.hohoanghai.weatherforecast.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hohoanghai.weatherforecast.model.City
import com.hohoanghai.weatherforecast.repository.CityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ListViewModel(private val cityRepository: CityRepository) : ViewModel() {
    private val _favoriteCities = MutableLiveData<List<City>>()
    val favoriteCities: LiveData<List<City>> get() = _favoriteCities

    private val _suggestionCities = MutableLiveData<List<City>>()
    val suggestionCities: LiveData<List<City>> get() = _suggestionCities

    private val _currentCity = MutableLiveData<City?>()
    val currentCity: LiveData<City?> get() = _currentCity

    val searchTextFlow = MutableStateFlow("")

    init {
        loadFavoriteCities()
    }

    private fun loadFavoriteCities() {
        viewModelScope.launch {
            cityRepository.getFavoriteCitiesFlow().collect {
                _favoriteCities.postValue(it)
            }
        }
    }

    fun searchCities(searchText: String) {
        val trimmedSearchText = searchText.trim()
        if (trimmedSearchText.isEmpty()) {
            _suggestionCities.postValue(listOf())
        } else {
            viewModelScope.launch {
                val suggestions = cityRepository.search(trimmedSearchText)
                _suggestionCities.postValue(suggestions)
            }
        }
    }

    fun onCitySelected(city: City) {
        _currentCity.postValue(city)
    }

    fun resetState() {
        searchCities("")
        _currentCity.postValue(null)
    }
}