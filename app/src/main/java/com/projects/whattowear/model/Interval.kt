package com.projects.whattowear.model

data class Interval(
    val startTime: String,
    val values: Temperature,
    val weatherType: DayWeatherType
)
