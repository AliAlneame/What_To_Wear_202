package com.projects.whattowear.network

import android.util.Log
import com.projects.whattowear.BuildConfig
import com.projects.whattowear.model.Interval
import com.projects.whattowear.utils.Constants
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class ApiClient(private val utils: NetworkUtils) {
    var intervals = listOf<Interval>()
    //var intervals: List<Interval>? = null

    private val client: OkHttpClient by lazy {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private val httpUrl =
        HttpUrl.Builder().scheme(Constants.SCHEME).host(Constants.BASE_URL).addPathSegments("v4")
            .addPathSegment("timelines").addQueryParameter("apikey", BuildConfig.apikey)
            .addQueryParameter("fields", Constants.FIELDS)
            .addQueryParameter("location", Constants.LOCATION)
            .addQueryParameter("timesteps", Constants.TIME_STEPS)
            .addQueryParameter("timezone", Constants.TIME_ZONE).build()


    fun makeRequest(callback: (List<Interval>?, String?) -> Unit) {
        val request = Request.Builder().url(httpUrl).build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback(null, e.message)
                Log.i("HI", "fail ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val jsonArray = utils.getIntervalsJsonArrayFromJson(jsonString!!)
                    intervals = utils.parseIntervals(jsonArray)
//                    getIntervalsList(_intervals)
//                    if (intervals[0].startTime =! )
                    callback(intervals, null)

                    Log.i("HI", intervals.toString())
                }
            }
        })
    }


}