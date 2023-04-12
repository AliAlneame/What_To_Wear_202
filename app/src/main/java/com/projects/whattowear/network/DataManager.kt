package com.projects.whattowear.network

import android.util.Log
import com.projects.whattowear.R
import com.projects.whattowear.model.DayWeatherType
import java.text.SimpleDateFormat
import java.util.*

class DataManager(private val client: ApiClient) {

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

    fun getRandomClothe(): Int {
        val todayWeatherType = client.intervals[0].weatherType
        Log.i("hio",client.intervals.toString())
        return getClothesList(todayWeatherType).shuffled()[0]
    }

    fun getDayName(dateString: String,formatPattern:String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeak = calendar.get(Calendar.DAY_OF_WEEK)
        return SimpleDateFormat(formatPattern, Locale.getDefault()).format(date)
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
