package com.projects.whattowear.model

import androidx.annotation.DrawableRes

data class Interval(
    val startTime: String,
    val values: Temperature,
    val weatherType: DayWeatherType,
    @DrawableRes val weatherImageId: Int,
)
