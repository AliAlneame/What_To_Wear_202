package com.projects.whattowear.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.whattowear.databinding.FragmentHomeBinding
import com.projects.whattowear.local.PrefsUtil
import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.NetworkUtils
import com.projects.whattowear.network.ApiClient
import com.projects.whattowear.network.DataManager

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
                    PrefsUtil.todayStartTime = todayWeather.startTime
                    PrefsUtil.intervalsImageIdList = intervalsList.joinToString(separator = ",") { it.clothesImageId.toString() }
                    setupBinding(todayWeather)
                }
            }
        }
        return binding.root
    }

    private fun init(daysAdapter: DaysAdapter) {
        utils = NetworkUtils()
        client = ApiClient(utils)
        data = DataManager()
        binding.recyclerViewDays.adapter = daysAdapter
        daysAdapter.submitList(listOf())
        PrefsUtil.initPrefs(requireActivity())
    }

    @SuppressLint("SetTextI18n")
    private fun setupBinding(todayWeather: Interval) {
        binding.apply {
            textDayDate.text = data.getDayName(todayWeather.startTime.substringBefore("T"), "EEEE")
            imageWeather.setImageResource(todayWeather.weatherImageId)
            textDegree.text = "${todayWeather.values.temperatureAvg}°c"
            imageClothes.setImageResource(todayWeather.clothesImageId)
            textOurPick.text = if (todayWeather.startTime == client.intervals!![0].startTime) {
                "Here is our pick for you today"
            } else {
                "Here is our pick for your ${data.getDayName(todayWeather.startTime, "EEEE")}"
            }
            textToday.text = if (todayWeather.startTime == client.intervals!![0].startTime) {
                "Today"
            } else {
                data.getDayName(todayWeather.startTime.substringBefore("T"), "EEEE").apply {
                    textDayDate.text = todayWeather.startTime.substringBefore("T")
                }
            }
            textLocation.visibility = View.VISIBLE
        }
    }


}