package com.umb.myapplication.features.rfn004.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URI
import java.util.concurrent.TimeUnit

class Rfn004ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    var puntos = MutableLiveData<String>()
    val songList = listOf<Int>(R.raw.instruccion, R.raw.carro, R.raw.casa, R.raw.perro)
    val resCorrect = listOf<Boolean>(false, true, false, true)
    var resUser: MutableList<Boolean> = mutableListOf()
    var index: Int = 0
    var mediaPlayer: MediaPlayer = MediaPlayer.create(context, songList[index])
    var startTime: Int = 0
    var finalTime: Int = 0

    init {
        actionButtoniniciar()

    }


    fun actionButtoniniciar() {
        mediaPlayer.start()
        finalTime = mediaPlayer.duration
        startTime = mediaPlayer.currentPosition
    }

    fun playNext(button1: Button, button2: Button) {
        if (songList.size > index) {
            mediaPlayer.stop()
            index += 1
            mediaPlayer = MediaPlayer.create(context, songList[index])
            mediaPlayer.start()
            finalTime = mediaPlayer.duration
            startTime = mediaPlayer.currentPosition
            GlobalScope.launch(Dispatchers.Main) {
                button1.isEnabled = false
                button2.isEnabled = false
                withContext(Dispatchers.IO) {
                    Thread.sleep(finalTime.toLong())
                }
                button1.isEnabled = true
                button2.isEnabled = true
            }
        }
    }

    fun saveRes(button1: Button) {
        var name: String = button1.id.toString()
        if (name.equals("buttonEquals")) {
            resUser.add(true)
            consultRes(true)
        } else if (name.equals("buttonDifferent")) {
            consultRes(false)
            resUser.add(false)
        }
    }

    fun consultRes (boolean: Boolean){
        if (boolean==resCorrect.get(index))
            if(!puntos.value.isNullOrEmpty() )
            puntos.value = (puntos.value!!.toInt() + 1).toString()
    }
}