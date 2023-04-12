package com.projects.whattowear.network

import com.projects.whattowear.model.Interval

class DataManager(private val utils: NetworkUtils, private val client: ApiClient) {

    private var intervalsList = listOf<Interval>()

    private fun getIntervalsList(): List<Interval> {
        client.makeRequest { intervals, message ->
            intervalsList = if (message != null) {
                listOf()
            } else {
                intervals!!
            }
        }
        return intervalsList
    }

//    fun getDaysWeatherTypeList(): List<DayWeatherType> {
//        val dayWeatherTypeList = mutableListOf<DayWeatherType>()
//        if (getIntervalsList().isNotEmpty())
//            for (interval in getIntervalsList()) {
//                val temperature = interval.values.temperatureAvg
//                val weatherType = when {
//                    temperature < 15.0 -> {
//                        DayWeatherType.COLD
//                    }
//                    temperature in 15.0..30.0 -> {
//                        DayWeatherType.WORM
//                    }
//                    else -> {
//                        DayWeatherType.HOT
//                    }
//                }
//                dayWeatherTypeList.add(weatherType)
//            }
//        return dayWeatherTypeList
//    }


}
