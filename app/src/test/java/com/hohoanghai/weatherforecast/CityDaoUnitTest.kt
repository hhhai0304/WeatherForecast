package com.hohoanghai.weatherforecast

import com.hohoanghai.weatherforecast.database.dao.CityDao
import com.hohoanghai.weatherforecast.database.dao.CityDaoImp
import com.hohoanghai.weatherforecast.database.entity.CityEntity
import com.hohoanghai.weatherforecast.repository.CityRepository
import com.hohoanghai.weatherforecast.repository.CityRepositoryImpl
import com.hohoanghai.weatherforecast.ui.list.viewmodel.ListViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityDaoUnitTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var realm: Realm
    private lateinit var cityDao: CityDao
    private lateinit var cityRepository: CityRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val config = RealmConfiguration.Builder(
            schema = setOf(
                CityEntity::class,
            ),
        ).name("test.realm").schemaVersion(1).build()
        realm = Realm.open(config)
        cityDao = CityDaoImp(realm, CityEntity::class)
        cityRepository = CityRepositoryImpl(cityDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testInsertFavorite() = runBlockingTest {
        val listViewModel = ListViewModel(cityRepository)
        cityDao.insert(CityEntity(1L, "Test Favorite", isFavorite = true))

        val model =
            listViewModel.favoriteCities.value?.find { it.isFavorite && it.id == 1L && it.name == "Test Favorite" }

        assertNotNull(model)
    }
}