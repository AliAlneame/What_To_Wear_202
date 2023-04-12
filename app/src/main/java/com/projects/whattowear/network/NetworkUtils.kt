package com.projects.whattowear.network

import android.util.Log
import com.projects.whattowear.R
import com.projects.whattowear.model.DayWeatherType
import com.projects.whattowear.model.Interval
import com.projects.whattowear.model.Temperature
import org.json.JSONArray
import org.json.JSONObject

class NetworkUtils {


    fun getIntervalsJsonArrayFromJson(response: String): JSONArray {
        val jsonObject = JSONObject(response)
        return jsonObject.getJSONObject("data")
            .getJSONArray("timelines")
            .getJSONObject(0)
            .getJSONArray("intervals")
    }

    fun parseIntervals(intervals: JSONArray): List<Interval> {
        val intervalsList = mutableListOf<Interval>()
        for (i in 0 until intervals.length()) {
            val interval = intervals.getJSONObject(i)
            val startTime = interval.getString("startTime")
            val values = interval.getJSONObject("values")
            val temperature = Temperature(
                temperatureAvg = values.getString("temperatureAvg").toString().toDouble(),
                temperatureMax = values.getString("temperatureMax").toString().toDouble(),
                temperatureMin = values.getString("temperatureMin").toString().toDouble(),
            )
            var weatherType = DayWeatherType.HOT
            var weatherImageId = 0

            getWeatherTypeAndImage(temperature) { weather, imageId ->
                weatherType = weather
                weatherImageId = imageId
            }


            intervalsList.add(Interval(startTime, temperature, weatherType, weatherImageId))
        }

        Log.i("hiiiiiiiii", intervalsList.toString())
        return intervalsList
    }

    private fun getWeatherTypeAndImage(temperature: Temperature, callback: (DayWeatherType, Int) -> Unit) {
        when {
            temperature.temperatureAvg < 20.0 -> {
                callback(DayWeatherType.COLD, R.drawable.svg_cold)
            }
            temperature.temperatureAvg in 20.0..25.0 -> {
                callback(DayWeatherType.WORM, R.drawable.svg_worm)
            }
            else -> {
                callback(DayWeatherType.HOT, R.drawable.svg_hot)
            }
        }
    }


}




