package com.projects.whattowear.network

import android.util.Log
import com.projects.whattowear.model.DayWeatherType
import com.projects.whattowear.model.Interval
import com.projects.whattowear.model.Temperature
import org.json.JSONArray
import org.json.JSONObject

class NetworkUtils {
    private val intervalsList = mutableListOf<Interval>()

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
            val weatherType = when {
                temperature.temperatureAvg < 15.0 -> { DayWeatherType.COLD }
                temperature.temperatureAvg in 15.0..30.0 -> { DayWeatherType.WORM }
                else -> { DayWeatherType.HOT }
            }

            intervalsList.add(Interval(startTime, temperature,weatherType))

        }
        Log.i("hiiiiiiiii",intervalsList.toString())
        return intervalsList

    }





}