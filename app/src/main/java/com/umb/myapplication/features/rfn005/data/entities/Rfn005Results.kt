package com.umb.myapplication.features.rfn005.data.entities

data class Rfn005Results(
    val respuestasCorrectas: Int,
    val respuestasOmitidas: Int,
    val respuestasIncorrectas: Int,
    val tiempo: Long
)