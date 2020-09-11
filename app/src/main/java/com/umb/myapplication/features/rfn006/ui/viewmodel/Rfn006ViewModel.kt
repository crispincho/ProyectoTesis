package com.umb.myapplication.features.rfn006.ui.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.R
import com.umb.myapplication.features.rfn006.ui.Rfn006Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Rfn006ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    var navigator: Rfn006Navigator? = null
    val score = MutableLiveData<Int>()
    var idUser: String? = null
    private lateinit var startDate: Date

    init {
        val mediaPlayer = MediaPlayer.create(context, R.raw.rfn006_instrucciones)
        mediaPlayer.start()
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Thread.sleep(mediaPlayer.duration.toLong())
            }
            navigator?.activateViews()
            startDate = Date()
        }
    }
}