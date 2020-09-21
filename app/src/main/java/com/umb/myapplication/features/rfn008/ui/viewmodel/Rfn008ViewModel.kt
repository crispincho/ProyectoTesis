package com.umb.myapplication.features.rfn008.ui.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.R
import com.umb.myapplication.features.rfn008.data.Rfn008Repository
import com.umb.myapplication.features.rfn008.ui.Rfn008Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Rfn008ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    var idUser: String? = null
    var navigator: Rfn008Navigator? = null

    val textBoxOne = MutableLiveData<String>()
    val textBoxTwo = MutableLiveData<String>()
    val textBoxThree = MutableLiveData<String>()
    val textBoxFour = MutableLiveData<String>()
    val textBoxFive = MutableLiveData<String>()
    val textBoxSix = MutableLiveData<String>()
    val textBoxSeven = MutableLiveData<String>()

    val showBoxOne = MutableLiveData<Boolean>()
    val showBoxTwo = MutableLiveData<Boolean>()
    val showBoxThree = MutableLiveData<Boolean>()
    val showBoxFour = MutableLiveData<Boolean>()
    val showBoxFive = MutableLiveData<Boolean>()
    val showBoxSix = MutableLiveData<Boolean>()
    val showBoxSeven = MutableLiveData<Boolean>()

    var score = MutableLiveData<Int>()

    private lateinit var startDate: Date

    private val listAnswers = listOf(
        "nb",
        "sl",
        "gcf",
        "tpj",
        "dmgy",
        "mrjd",
        "pbnkf",
        "|ll|ltñs",
        "clkyd|ll|",
        "mpgnts",
        "kgntfdl",
        "gtdmp|ll|c"
    )
    private val listAudios = listOf(
        R.raw.rfn008_n_b,
        R.raw.rfn008_s_l,
        R.raw.rfn008_g_c_f,
        R.raw.rfn008_t_p_j,
        R.raw.rfn008_d_m_g_y,
        R.raw.rfn008_m_r_j_d,
        R.raw.rfn008_p_b_n_k_f,
        R.raw.rfn008_ll_l_t_enie_s,
        R.raw.rfn008_c_l_k_y_d_ll,
        R.raw.rfn008_m_p_g_n_t_s,
        R.raw.rfn008_k_g_n_t_f_d_l,
        R.raw.rfn008_d_t_d_m_p_ll_c
    )
    private var index = -1
    private var length = 0
    private var enableView = false

    init {
//        val mediaPlayer = MediaPlayer.create(context, R.raw.rfn008_instrucciones)
//        mediaPlayer.start()
//        GlobalScope.launch(Dispatchers.Main) {
//            withContext(Dispatchers.IO) {
//                Thread.sleep(mediaPlayer.duration.toLong())
//            }
//            navigator?.activateViews()
//            startDate = Date()
//            validateSerie()
//        }
        showBoxOne.value = true
        showBoxTwo.value = true
        showBoxThree.value = true
        showBoxFour.value = true
        showBoxFive.value = true
        showBoxSix.value = true
        showBoxSeven.value = true
    }

    fun keyBoards(text: String) {
        if (!enableView)
            return
        if (text.isNotEmpty()) {
            when {
                textBoxOne.value.isNullOrEmpty() -> {
                    textBoxOne.value = text
                }
                textBoxTwo.value.isNullOrEmpty() -> {
                    textBoxTwo.value = text
                }
                textBoxThree.value.isNullOrEmpty() -> {
                    textBoxThree.value = text
                }
                textBoxFour.value.isNullOrEmpty() -> {
                    textBoxFour.value = text
                }
                textBoxFive.value.isNullOrEmpty() -> {
                    textBoxFive.value = text
                }
                textBoxSix.value.isNullOrEmpty() -> {
                    textBoxSix.value = text
                }
                textBoxSeven.value.isNullOrEmpty() -> {
                    textBoxSeven.value = text
                }
            }
        }
        validateSerie()
    }

    fun cleanBoxes() {
        textBoxOne.value = ""
        textBoxTwo.value = ""
        textBoxThree.value = ""
        textBoxFour.value = ""
        textBoxFive.value = ""
        textBoxSix.value = ""
        textBoxSeven.value = ""
    }

    fun validateSerie() {
        val chars = when {
            textBoxOne.value.isNullOrEmpty() -> 0
            textBoxTwo.value.isNullOrEmpty() -> 1
            textBoxThree.value.isNullOrEmpty() -> 2
            textBoxFour.value.isNullOrEmpty() -> 3
            textBoxFive.value.isNullOrEmpty() -> 4
            textBoxSix.value.isNullOrEmpty() -> 5
            textBoxSeven.value.isNullOrEmpty() -> 6
            else -> 7
        }
        if (chars >= length) {
            if (++index >= listAnswers.size) {
                Rfn008Repository.initFirebase(context)
                Rfn008Repository.sendData(idUser!!, Date().time - startDate.time, score.value!!)
                navigator?.toNextActvity()
                return
            }
            scoreSerie()
            length = 0
            var starSpecialChar = false
            listAnswers[index].forEach {
                if ('|' == it) {
                    if (starSpecialChar) {
                        starSpecialChar = !starSpecialChar
                        length++
                    } else {
                        starSpecialChar = !starSpecialChar
                    }
                } else if (!starSpecialChar) {
                    length++
                }
            }
            playAudio()
            navigator?.changeNumberBoxes(length)
        }
    }

    private fun playAudio() {
        enableView = false
        val mediaPlayer = MediaPlayer.create(context, listAudios[index])
        mediaPlayer.start()
        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(mediaPlayer.duration.toLong())
            enableView = true
        }
    }

    private fun scoreSerie() {
    }

}