package com.hohoanghai.weatherforecast.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hohoanghai.weatherforecast.R
import com.hohoanghai.weatherforecast.ui.detail.DetailState

@BindingAdapter("weatherIcon")
fun bindIconFromWeatherIcon(view: ImageView, icon: String?) {
    icon?.let {
        Glide.with(view.context)
            .load("${Constants.WEATHER_ICON_URL}$icon.png")
            .into(view)
    }
}

@BindingAdapter("showLoading")
fun bindDisplayProgressBar(view: View, state: DetailState) {
    if (state == DetailState.Loading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("dataState")
fun bindDataState(view: View, state: DetailState) {
    if (state == DetailState.Error) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("errorState")
fun bindErrorState(view: View, state: DetailState) {
    if (state == DetailState.Error) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("isFavorite")
fun bindFavoriteIcon(view: FloatingActionButton, isFavorite: Boolean?) {
    if (isFavorite == true) {
        view.setImageDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.ic_favorite
            )
        )
    } else {
        view.setImageDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.ic_favorite_outlined
            )
        )
    }
}

@BindingAdapter("temperature")
fun bindTemperature(view: TextView, temperature: Double) {
    if (temperature != 0.0) {
        view.text = view.context.getString(R.string.temperature, temperature.toInt())
    }
}