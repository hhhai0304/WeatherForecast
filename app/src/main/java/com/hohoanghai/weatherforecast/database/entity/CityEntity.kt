package com.hohoanghai.weatherforecast.database.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CityEntity(
    @PrimaryKey
    var id: Long = 0L,
    var name: String = "",
    var state: String = "",
    var country: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var isFavorite: Boolean = false,
    var searchKeyword: String = ""
) : RealmObject {
    constructor() : this(0L, "", "", "", 0.0, 0.0, false, "")
}