package com.umb.myapplication.features.formulario.data.entities

import java.util.*

data class User (
    val id:String= UUID.randomUUID().toString(),
    val name:String,
    val age:Int,
    val email:String,
    val course:String,
    val telephone:String,
    val guardianName:String,
    val relationship:String
)