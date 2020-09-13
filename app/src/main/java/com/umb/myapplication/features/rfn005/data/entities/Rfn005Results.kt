package com.umb.myapplication.features.rfn005.data.entities

data class Rfn005Results(
    val correctAnswers: Int,
    val omitedAnswers: Int,
    val wrongAnswers: Int,
    val time: Long
)