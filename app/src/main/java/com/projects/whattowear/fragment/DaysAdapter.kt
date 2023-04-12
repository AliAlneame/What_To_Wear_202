package com.projects.whattowear.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.whattowear.R
import com.projects.whattowear.databinding.ItemDayBinding
import com.projects.whattowear.model.DayWeatherType
import com.projects.whattowear.model.Interval

class DaysAdapter : ListAdapter<Interval, DaysAdapter.ItemViewHolder>(DaysDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDayBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ItemViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(interval: Interval) {
                binding.apply {
                    textTemperatureMax.text = "max ${interval.values.temperatureMax}°c"
                    textTemperatureMin.text = "min ${interval.values.temperatureMin}°c"
                    textItemDayDate.text = "${interval.startTime.substringBefore("T")}"
                    imageWeather.setImageResource(interval.weatherImageId)
                    textWeatherType.text = when(interval.weatherType) {
                        DayWeatherType.HOT -> "Sunny"
                        DayWeatherType.COLD -> "Cold"
                        else -> "Worm"
                    }
                }
            }

    }

    class DaysDiffUtil() : DiffUtil.ItemCallback<Interval>() {
        override fun areItemsTheSame(oldItem: Interval, newItem: Interval): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Interval, newItem: Interval): Boolean {
            return oldItem == newItem
        }
    }
}