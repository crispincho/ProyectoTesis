package com.umb.myapplication.features.rfn006.data.entities

data class Rfn006Result(
    val score: Int,
    val time: Long,
    val listWords: List<Word>
)