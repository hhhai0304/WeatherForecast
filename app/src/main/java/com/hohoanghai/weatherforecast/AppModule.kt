package com.hohoanghai.weatherforecast

import com.hohoanghai.weatherforecast.database.dao.CityDao
import com.hohoanghai.weatherforecast.database.dao.CityDaoImp
import com.hohoanghai.weatherforecast.database.entity.CityEntity
import com.hohoanghai.weatherforecast.network.WeatherService
import com.hohoanghai.weatherforecast.network.interceptor.AuthenticationInterceptor
import com.hohoanghai.weatherforecast.repository.CityRepository
import com.hohoanghai.weatherforecast.ui.detail.DetailViewModel
import com.hohoanghai.weatherforecast.ui.list.ListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.reflect.KClass

val entityModule = module {
    single<KClass<CityEntity>> { CityEntity::class }
}

val databaseModule = module {
    single<Realm> {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                CityEntity::class,
            ),
        ).name("db.realm").schemaVersion(1).build()
        Realm.open(config)
    }
}

val daoModule = module {
    factory<CityDao> { CityDaoImp(realm = get(), clazz = get()) }
}

val networkModule = module {
    val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthenticationInterceptor())
        .addInterceptor(HttpLoggingInterceptor())

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .client(httpClient.build())
        .build()

    single<Retrofit> { retrofit }
    single<WeatherService> { retrofit.create(WeatherService::class.java) }
}

val repositoryModule = module {
    factory { CityRepository(get()) }
}

val viewModelModule = module {
    viewModel {
        ListViewModel(get())
        DetailViewModel(get())
    }
}