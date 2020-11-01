package com.umb.myapplication.features.rfn004.ui

interface Rfn004Navigator {

    fun getUserID():String
    fun toNextActvity(idUser: String, guardianCode: String, code: String)
    fun activateTest()
}