package com.hohoanghai.weatherforecast.model

import android.os.Parcelable
import com.hohoanghai.weatherforecast.database.entity.CityEntity
import com.hohoanghai.weatherforecast.util.unaccented
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    @Json(name = "coord") val coordinate: Coordinate,
) : Parcelable {
    constructor(entity: CityEntity) : this(
        entity.id,
        entity.name,
        entity.state,
        entity.country,
        Coordinate(entity.latitude, entity.longitude)
    )

    fun toEntity(): CityEntity = CityEntity(
        id = id,
        name = name,
        state = state,
        country = country,
        latitude = coordinate.latitude,
        longitude = coordinate.longitude,
        searchKeyword = name.unaccented().lowercase(),
    )
}
