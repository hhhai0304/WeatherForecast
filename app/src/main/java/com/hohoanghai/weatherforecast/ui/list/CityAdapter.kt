package com.hohoanghai.weatherforecast.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hohoanghai.weatherforecast.databinding.ItemListBinding
import com.hohoanghai.weatherforecast.model.City


class CityAdapter(private val onClickListener: (it: City) -> Unit) :
    ListAdapter<City, CityAdapter.CityViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class CityViewHolder(private var binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.city = city
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)

        holder.also {
            it.itemView.setOnClickListener {
                onClickListener(city)
            }
            it.bind(city)
        }
    }
}