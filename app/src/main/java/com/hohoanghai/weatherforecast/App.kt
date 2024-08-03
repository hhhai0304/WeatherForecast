package com.hohoanghai.weatherforecast

import android.app.Application
import com.hohoanghai.weatherforecast.repository.CityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        setupLogger()
        setupDI()
        initData()
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupDI() {
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    entityModule,
                    databaseModule,
                    daoModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    private fun initData() {
        val cityRepository: CityRepository by inject()
        applicationScope.launch {
            cityRepository.initCities(this@App)
        }
    }
}