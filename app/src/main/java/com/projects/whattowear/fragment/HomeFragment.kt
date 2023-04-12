package com.projects.whattowear.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.whattowear.databinding.FragmentHomeBinding
import com.projects.whattowear.network.NetworkUtils
import com.projects.whattowear.network.ApiClient

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var utils: NetworkUtils
    private lateinit var client: ApiClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        utils = NetworkUtils()
        client = ApiClient(utils)
        val daysAdapter = DaysAdapter()
        binding.recyclerViewDays.adapter = daysAdapter
        daysAdapter.submitList(listOf())

        client.makeRequest { intervalsList, message ->
            if (message != null) {
                Log.e("error", message)
            } else {
                requireActivity().runOnUiThread {
                    daysAdapter.submitList(intervalsList)
                    val todayWeather =  intervalsList!![0]
                    binding.apply {
                        textDayDate.text = todayWeather.startTime.substringBefore("T")
                        imageWeather.setImageResource(todayWeather.weatherImageId)
                        textDegree.text = "${todayWeather.values.temperatureMin}°c"
                    }
                }
            }
        }
        return binding.root
    }


}