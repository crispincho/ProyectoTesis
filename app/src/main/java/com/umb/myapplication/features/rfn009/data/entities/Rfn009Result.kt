package com.umb.myapplication.features.rfn009.data.entities

data class Rfn009Result(
    val score: Int,
    val time: Long,
    val resUserText:MutableList<String>
)