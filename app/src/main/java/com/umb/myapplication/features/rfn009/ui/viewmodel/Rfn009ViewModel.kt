package com.umb.myapplication.features.rfn009.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.features.rfn009.ui.Rfn009Navigator

class Rfn009ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    lateinit var navigator: Rfn009Navigator

    lateinit var idUser: String

    val score = MutableLiveData<String>()

}