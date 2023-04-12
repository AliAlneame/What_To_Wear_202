package com.projects.whattowear.network

import android.util.Log
import com.projects.whattowear.R
import com.projects.whattowear.model.DayWeatherType
import com.projects.whattowear.model.Interval
import com.projects.whattowear.model.Temperature
import org.json.JSONArray
import org.json.JSONObject

class NetworkUtils {

    val intervalsList = mutableListOf<Interval>()

    fun getIntervalsJsonArrayFromJson(response: String): JSONArray {
        val jsonObject = JSONObject(response)
        return jsonObject.getJSONObject("data")
            .getJSONArray("timelines")
            .getJSONObject(0)
            .getJSONArray("intervals")
    }

    fun parseIntervals(intervals: JSONArray): List<Interval> {

        for (i in 0 until intervals.length()) {
            val interval = intervals.getJSONObject(i)
            val startTime = interval.getString("startTime")
            val values = interval.getJSONObject("values")
            val temperature = Temperature(
                temperatureAvg = values.getString("temperatureAvg").toString().toDouble(),
                temperatureMax = values.getString("temperatureMax").toString().toDouble(),
                temperatureMin = values.getString("temperatureMin").toString().toDouble(),
            )
            val weatherType = getWeatherTypeAndImageId(temperature).first
            val imageId = getWeatherTypeAndImageId(temperature).second

            intervalsList.add(Interval(startTime, temperature, weatherType, imageId))
        }

        Log.i("hiiiiiiiii", intervalsList.toString())
        return intervalsList
    }

    private fun getWeatherTypeAndImageId(temperature: Temperature):Pair<DayWeatherType,Int> {
        return when {
            temperature.temperatureAvg < 20.0 -> {
                Pair(DayWeatherType.COLD ,R.drawable.svg_cold)
            }
            temperature.temperatureAvg in 20.0..25.0 -> {
                Pair(DayWeatherType.WORM ,R.drawable.svg_worm)
            }
            else -> {
                Pair(DayWeatherType.HOT ,R.drawable.svg_hot)
            }
        }
    }









//    private fun getWeatherImage(temperature: Temperature): Int {
//        return when {
//            temperature.temperatureAvg < 20.0 -> {
//                R.drawable.svg_cold
//            }
//            temperature.temperatureAvg in 20.0..25.0 -> {
//                R.drawable.svg_worm
//            }
//            else -> {
//                R.drawable.svg_hot
//            }
//        }
//    }


}




