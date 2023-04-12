package com.projects.whattowear.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.whattowear.databinding.FragmentHomeBinding
import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.NetworkUtils
import com.projects.whattowear.network.ApiClient
import com.projects.whattowear.network.DataManager
import okhttp3.internal.http2.Http2Connection

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var utils: NetworkUtils
    private lateinit var client: ApiClient
    private lateinit var data: DataManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val daysAdapter = DaysAdapter(::setupBinding)
        init(daysAdapter)

        client.makeRequest { intervalsList, message ->
            if (message != null) {
                Log.e("error", message)
            } else {
                requireActivity().runOnUiThread {
                    daysAdapter.submitList(intervalsList)
                    val todayWeather = intervalsList!![0]
                    setupBinding(todayWeather)
                }
            }
        }
        return binding.root
    }

    private fun init(daysAdapter: DaysAdapter) {
        utils = NetworkUtils()
        client = ApiClient(utils)
        data = DataManager(client)
        binding.recyclerViewDays.adapter = daysAdapter
        daysAdapter.submitList(listOf())
    }

    private fun setupBinding(todayWeather: Interval) {
        binding.apply {
            textDayDate.text = data.getDayName(todayWeather.startTime.substringBefore("T"),"EEEE")
            imageWeather.setImageResource(todayWeather.weatherImageId)
            textDegree.text = "${todayWeather.values.temperatureMin}°c"
            imageClothes.setImageResource(data.getRandomClothe())
            textOurPick.text = if (todayWeather.startTime == client.intervals[0].startTime) {
                "Here is our pick for you today"
            } else {
                "Here is our pick for your ${data.getDayName(todayWeather.startTime,"EEEE")}"
            }

        }
    }


}