package com.projects.whattowear.network

import com.projects.whattowear.R
import com.projects.whattowear.model.DayWeatherType
import com.projects.whattowear.model.Temperature
import java.text.SimpleDateFormat
import java.util.*

class DataManager(
    //private val client: ApiClient
) {

    private fun getClothesList(dayWeatherType: DayWeatherType): List<Int> {
        return when (dayWeatherType) {
            DayWeatherType.COLD -> {
                listOf(
                    R.drawable.image_cold_1,
                    R.drawable.image_cold_2,
                    R.drawable.image_cold_3
                )
            }
            DayWeatherType.WORM -> listOf(
                R.drawable.image_worm_1,
                R.drawable.image_worm_2,
                R.drawable.image_worm_3,
                R.drawable.image_worm_4,
            )
            else -> listOf(
                R.drawable.image_hot_1,
                R.drawable.image_hot_2,
                R.drawable.image_hot_3,
            )
        }
    }

    fun getDayName(dateString: String, formatPattern: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        val dayOfWeak = calendar.get(Calendar.DAY_OF_WEEK)
        return SimpleDateFormat(formatPattern, Locale.getDefault()).format(date)
    }

    fun getWeatherAndClothesImageId(dayWeatherType: DayWeatherType): Pair<Int, Int> {
        return when (dayWeatherType) {
            DayWeatherType.COLD -> {
                Pair(R.drawable.svg_cold,getClothesList(dayWeatherType).random())
            }
            DayWeatherType.WORM -> {
                Pair(R.drawable.svg_worm,getClothesList(dayWeatherType).random())
            }
            else -> {
                Pair(R.drawable.svg_hot,getClothesList(dayWeatherType).random())
            }
        }
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


    //    fun getRandomClothe(): Int {
//        var intervalsList = listOf<Interval>()
//        client.makeRequest { intervals, message ->
//            if (message != null) {
//                Log.i("hio",message)
//            } else {
//                intervalsList = intervals!!
//                Log.i("hiio",intervalsList.toString())
//            }
//        }
//        val todayWeatherType = intervalsList[0].weatherType
//        Log.i("hio",intervalsList.toString())
//        return getClothesList(todayWeatherType).random()
//    }

}
