package com.umb.myapplication.features.rfn006.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class Rfn006ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    val score = MutableLiveData<Int>()

    var idUser: String? = null
}