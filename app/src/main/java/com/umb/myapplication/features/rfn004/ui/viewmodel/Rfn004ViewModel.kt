package com.umb.myapplication.features.rfn004.ui.viewmodel


import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.R
import com.umb.myapplication.features.rfn004.data.Rfn004Repository
import com.umb.myapplication.features.rfn004.ui.Rfn004Navigator
import kotlinx.android.synthetic.main.activity_rfn004.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Rfn004ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {
    var puntos = MutableLiveData<String>()
    var navigator: Rfn004Navigator?=null
    private val songList = listOf(R.raw.rfn004_banio_panio, R.raw.rfn004_via_dia,
                                R.raw.rfn004_capa_cana, R.raw.rfn004_foto_foco, R.raw.rfn004_unia_unia,
                                R.raw.rfn004_foca_foca, R.raw.rfn004_cania_calla, R.raw.rfn004_carro_jarro,
                                R.raw.rfn004_caja_caja, R.raw.rfn004_paso_peso, R.raw.rfn004_misa_mesa,
                                R.raw.rfn004_gato_gato, R.raw.rfn004_callo_callo, R.raw.rfn004_mano_mano,
                                R.raw.rfn004_cama_cama, R.raw.rfn004_pantera_bandera, R.raw.rfn004_sifon_sillon,
                                 R.raw.rfn004_pasa_pasa, R.raw.rfn004_vara_bala)
    private val resCorrect = listOf(false, false, false, false,true,true,false,false,true,false,false,true,true,true,true,false,false,true,false)
    private var resUser: MutableList<Boolean> = mutableListOf()
    private var index: Int = 0
    private var mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.rfn004_ejemplo_instructivo)
    private var startTime: Int = 0
    private var finalTime: Int = 0
    private var dateIninitial:Date = Date()

    init {
        playInitSound()
        puntos.value="0"
    }

    fun playNext(button1: Button, button2: Button) {
        if (songList.size > index) {
            mediaPlayer = MediaPlayer.create(context, songList[index])
            index += 1
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

            saveRes(button1)
        }else{
            var dateFin =
            Rfn004Repository.initFirebase(context)
            Rfn004Repository.insertResultTestRFN004(navigator!!.getUserID(), puntos.value!!.toInt(), Date().time - dateIninitial.time)
            navigator!!.toNextActvity(navigator!!.getUserID())
        }
    }

    private fun saveRes(button1: Button) {
        val name: String = button1.id.toString()
        when (button1.id){
            R.id.buttonEquals -> {
                    resUser.add(true)
                    consultRes(true)
            }
            R.id.buttonDifferent ->{
                consultRes(false)
                resUser.add(false)
            }
        }
    }

    private fun consultRes (boolean: Boolean){
        if (boolean== resCorrect[index])
            puntos.value = (puntos.value!!.toInt() + 1).toString()
    }

    fun playInitSound(){
        val thread =Thread(Runnable {
            GlobalScope.launch (Dispatchers.Main) {
                mediaPlayer.start()
                finalTime = mediaPlayer.duration
                startTime = mediaPlayer.currentPosition
                withContext(Dispatchers.IO){
                    Thread.sleep(finalTime.toLong())
                }
                navigator!!.activateTest()
            }
        })
        thread.start()
    }


}