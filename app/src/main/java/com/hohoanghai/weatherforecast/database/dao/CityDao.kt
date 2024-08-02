package com.hohoanghai.weatherforecast.database.dao

import com.hohoanghai.weatherforecast.database.entity.CityEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.query.RealmResults
import timber.log.Timber
import kotlin.reflect.KClass

interface CityDao : RealmDao<CityEntity> {
    suspend fun selectByName(name: String, limit: Int?): RealmResults<CityEntity>
    suspend fun selectFavorites(): RealmResults<CityEntity>
    suspend fun isEmpty(): Boolean
    suspend fun toggleFavorite(id: Long)
}

class CityDaoImp(override val realm: Realm, override val clazz: KClass<CityEntity>) : CityDao {
    override suspend fun selectByName(name: String, limit: Int?): RealmResults<CityEntity> {
        var limitQuery = ""
        if (limit != null) {
            limitQuery = " LIMIT($limit)"
        }
        return realm.query(
            clazz,
            "searchKeyword CONTAINS $0 DISTINCT(searchKeyword)$limitQuery",
            name.lowercase()
        ).find()
    }

    override suspend fun selectFavorites(): RealmResults<CityEntity> {
        return realm.query(clazz, "isFavorite == $0", true).find()
    }

    override suspend fun isEmpty(): Boolean {
        return realm.query(clazz).first().find() == null
    }

    override suspend fun toggleFavorite(id: Long) {
        realm.write {
            val city = this.query(clazz, "id == $0", id).first().find()
            Timber.i("HAINE toggleFavorite: $city")
            city?.let {
                it.isFavorite = !it.isFavorite
            }
        }
    }
}