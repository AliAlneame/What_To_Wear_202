package com.projects.whattowear.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projects.whattowear.R
import com.projects.whattowear.local.PrefsUtil
import com.projects.whattowear.model.DayWeatherType
import com.projects.whattowear.model.Interval
import com.projects.whattowear.model.Temperature
import java.text.SimpleDateFormat
import java.util.*

class DataManager(
) {

    val clothesCold = listOf(
        R.drawable.image_cold_1,
        R.drawable.image_cold_2,
        R.drawable.image_cold_3
    )
    val clothesHot = listOf(
        R.drawable.image_hot_1,
        R.drawable.image_hot_2,
        R.drawable.image_hot_3,
    )
    val clothesWorm = listOf(
        R.drawable.image_worm_1,
        R.drawable.image_worm_2,
        R.drawable.image_worm_3,
        R.drawable.image_worm_4,
    )

    private fun getClothesList(dayWeatherType: DayWeatherType): List<Int> {
        return when (dayWeatherType) {
            DayWeatherType.COLD -> clothesCold
            DayWeatherType.WORM -> clothesWorm
            else -> clothesHot
        }
    }

    fun getDayName(dateString: String, formatPattern: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        return SimpleDateFormat(formatPattern, Locale.getDefault()).format(date)
    }

    fun getWeatherImageId(dayWeatherType: DayWeatherType): Int {
        return when (dayWeatherType) {
            DayWeatherType.COLD -> {
                R.drawable.svg_cold
            }
            DayWeatherType.WORM -> {
                R.drawable.svg_worm
            }
            else -> {
                R.drawable.svg_hot
            }
        }
    }

    fun getClothesImageId(dayWeatherType: DayWeatherType): Int {
        return getClothesList(dayWeatherType).random()
    }


    fun getDayWeatherType(temperature: Temperature): DayWeatherType {
        return when {
            temperature.temperatureAvg < 20.0 -> {
                DayWeatherType.COLD
            }
            temperature.temperatureAvg in 20.0..25.0 -> {
                DayWeatherType.WORM
            }
            else -> {
                DayWeatherType.HOT
            }
        }
    }

//    var intervalsImageId = PrefsUtil.intervalsImageId


}
