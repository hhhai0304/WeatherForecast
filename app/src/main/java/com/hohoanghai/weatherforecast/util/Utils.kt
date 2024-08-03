package com.hohoanghai.weatherforecast.util

import java.text.Normalizer

fun CharSequence.unaccented(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return "\\p{InCombiningDiacriticalMarks}+".toRegex().replace(temp, "")
}