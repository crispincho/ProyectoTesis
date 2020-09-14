package com.umb.myapplication.features.rfn008.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.features.rfn008.ui.Rfn008Navigator

class Rfn008ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    var idUser: String? = null
    var navigator: Rfn008Navigator? = null

    var score = MutableLiveData<Int>()
}