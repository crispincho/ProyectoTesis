package com.umb.myapplication.features.rfn007.ui.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.umb.myapplication.R
import com.umb.myapplication.features.rfn007.data.Rfn007Repository
import com.umb.myapplication.features.rfn007.data.Rfn007Repository.insertResultTestRFN007
import com.umb.myapplication.features.rfn007.ui.Rfn007Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class Rfn007ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application)  {
    var idUser: String? = null
    var guardianUser: String? = null
    var sampleCode: String? = null
    var navigator: Rfn007Navigator? = null
    var score = MutableLiveData<Int>()
    private var resUser: MutableList<Boolean> = mutableListOf()
    private val songList = listOf(R.raw.rfn007_blusa,
        R.raw.rfn007_vagido, R.raw.rfn007_tropa, R.raw.rfn007_amplio,
        R.raw.rfn007_abuhado, R.raw.rfn007_chicharo, R.raw.rfn007_abotagado,
        R.raw.rfn007_cristobal, R.raw.rfn007_burdegano, R.raw.rfn007_inadvertencia,
        R.raw.rfn007_sobre, R.raw.rfn007_trabuco, R.raw.rfn007_ditirambo,
        R.raw.rfn007_senectud, R.raw.rfn007_quemar)
    private val listWork = listOf("blusa", "vagido", "tropa", "amplio",
        "abuhado","chícharo","abotagado","cristóbal","burdégano","inadvertencia",
        "sobre","trabuco","ditirambo","senectud","quemar")

    private var index: Int = 0
    private var mediaPlayer: MediaPlayer = MediaPlayer.create(context, songList[index])
    private var startTime: Int = 0
    private var finalTime: Int = 0
    private var dateIninitial:Date = Date()

    init {
        score.value=0
    }

    fun playNext(btSpeech: ImageView){
        btSpeech.isEnabled=false
        if (index==0){
            dateIninitial = Date()
            Log.d("indexCondition", index.toString())
            index=1;
        }
        if (songList.size > index) {
            mediaPlayer = MediaPlayer.create(context, songList[index])
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                btSpeech.isEnabled=true
                mediaPlayer.release()
            }
            finalTime = mediaPlayer.duration
            startTime = mediaPlayer.currentPosition
            startTime = mediaPlayer.currentPosition
            index+=1
        }else{
            Rfn007Repository.initFirebase(context)
            insertResultTestRFN007(idUser.toString(), score.value!!.toInt(),
                Date().time-dateIninitial.time, guardianUser.toString(),
                sampleCode.toString())
            navigator!!.toNextActvity()
        }
    }

    fun scored (boolean: Boolean){
        if (boolean){
            score.value = 1 + score.value!!
        }
    }

    fun evaluateText(text:String){
        Log.d("index", (index-2).toString())
        Log.d("valorAudio", text)
        Log.d("valorText", listWork[index-2])
        if(text.equals(listWork[index-2], ignoreCase = true)){
            resUser.add(true)
            Log.d("boolean", "verdad")
            scored(true)
        }else{  
            resUser.add(false)
        }
    }

    fun playInitSound(microphone: ImageView) {
        val thread =Thread(Runnable {
            viewModelScope.launch (Dispatchers.Main) {

                mediaPlayer.start()
                finalTime = mediaPlayer.duration
                startTime = mediaPlayer.currentPosition
                withContext(Dispatchers.IO){
                    Thread.sleep(finalTime.toLong())
                }
                microphone.isEnabled=true
                navigator!!.activateTest()
            }
        })
        thread.start()
    }

}