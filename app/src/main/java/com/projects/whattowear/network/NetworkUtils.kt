package com.projects.whattowear.network

import android.content.Context
import android.util.Log
import com.projects.whattowear.local.PrefsUtil
import com.projects.whattowear.model.Interval
import com.projects.whattowear.model.Temperature
import org.json.JSONArray
import org.json.JSONObject

class NetworkUtils {
    private val data = DataManager()

    private val intervalsList = mutableListOf<Interval>()
    private val startTimeAndImageIdPairs = mutableListOf<Pair<String, Int>>()

    fun getIntervalsJsonArrayFromJson(response: String): JSONArray {
        val jsonObject = JSONObject(response)
        return jsonObject.getJSONObject("data")
            .getJSONArray("timelines")
            .getJSONObject(0)
            .getJSONArray("intervals")
    }

    fun parseIntervals(intervals: JSONArray): List<Interval> {
        val sharePref = getStartTimeAndImageId()
        for (i in 0 until intervals.length()) {
            val interval = intervals.getJSONObject(i)
            val startTime = interval.getString("startTime")

            val values = interval.getJSONObject("values")
            val temperature = Temperature(
                temperatureAvg = values.getString("temperatureAvg").toString().toDouble(),
                temperatureMax = values.getString("temperatureMax").toString().toDouble(),
                temperatureMin = values.getString("temperatureMin").toString().toDouble(),
            )
            val weatherType = data.getDayWeatherType(temperature)
            val weatherImageId = data.getWeatherImageId(weatherType)
            val clothesImageId =
                if (i + 1 < intervals.length() && (startTime == sharePref?.get(i)?.first || startTime == sharePref?.get(
                        i + 1
                    )?.first)
                ) {
                    sharePref?.get(i)?.second
                } else {
                    data.getClothesImageId(weatherType, intervalsList)
                }

            val startTimeAndImageId = Pair(startTime, clothesImageId!!)
            startTimeAndImageIdPairs.add(startTimeAndImageId)
            intervalsList.add(
                Interval(startTime, temperature, weatherType, weatherImageId, clothesImageId)
            )
        }
        Log.i("hiiiiiiiii", intervalsList.toString())
        saveStartTimeAndImageId(startTimeAndImageIdPairs)
        Log.i("clothes", intervalsList.toString())
        return intervalsList

    }


    private fun saveStartTimeAndImageId(values: List<Pair<String, Int>>) {
        val delimiter1 = "|"
        val delimiter2 = ","
        val serializedPairs =
            values.joinToString(delimiter1) { "${it.first}$delimiter2${it.second}" }
        PrefsUtil.startTimeAndImageId = serializedPairs

    }

    private fun getStartTimeAndImageId(): List<Pair<String, Int>>? {
        val delimiter1 = "|"
        val delimiter2 = ","
        val serializedPairsArray = PrefsUtil.startTimeAndImageId?.split(delimiter1)?.toTypedArray()
        return serializedPairsArray?.map { serializedPair ->
            val pairValues = serializedPair.split(delimiter2)
            Pair(pairValues[0], pairValues[1].toInt())
        }
    }


}




