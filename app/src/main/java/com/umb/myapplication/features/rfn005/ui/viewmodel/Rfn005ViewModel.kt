package com.umb.myapplication.features.rfn005.ui.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.R
import com.umb.myapplication.features.rfn005.data.Rfn005Repository
import com.umb.myapplication.features.rfn005.data.entities.Rfn005Results
import com.umb.myapplication.features.rfn005.ui.Rfn005Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Rfn005ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    private val ROUNDS_NUMBER = 12
    val ROUNDS_TIME = 25000L
    private val ROUND_ANSWERS = 3
    private var round = 0

    var navigator: Rfn005Navigator? = null

    var idUser: String? = null

    private lateinit var startDate: Date

    private var score = 0
    private var wrongAnswers = 0
    private var omittedAnswers = 0

    val points = MutableLiveData<Int>()

    init {
        val mediaPlayer = MediaPlayer.create(context, R.raw.rfn005_instrucciones)
        mediaPlayer.start()
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Thread.sleep(mediaPlayer.duration.toLong())
            }
            navigator?.activateViews()
            startDate = Date()
        }
    }

    //Equivalencias direcciones arriba = 0 , derecha = 1 , izquierda = 2 , abajo = 3
    val buttonsMutableList = listOf<MutableLiveData<MutableList<Int>>>(
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData()
    )

    private fun generateRamdonDirections() {
        round++
        buttonsMutableList[19].value = mutableListOf((0..3).random(), 0)
        do {
            buttonsMutableList[18].value = mutableListOf((0..3).random(), 0)
        } while (buttonsMutableList[18].value!![0] == buttonsMutableList[19].value!![0])
        var position = 0
        var answers = 0
        do {
            buttonsMutableList[position].value = mutableListOf((0..3).random(), 0)
            if (position < 17 && position != 8 && answers < ROUND_ANSWERS && buttonsMutableList[position].value!![0] == buttonsMutableList[18].value!![0]) {
                position++
                answers++
                buttonsMutableList[position].value =
                    mutableListOf(buttonsMutableList[19].value!![0], 0)
            } else if (answers == ROUND_ANSWERS && buttonsMutableList[position].value!![0] == buttonsMutableList[18].value!![0]) {
                do {
                    buttonsMutableList[position + 1].value = mutableListOf((0..3).random(), 0)
                } while (isRoundAnswer(position))
                position++
            }
            position++
            if (position == 17 && answers < 3) {
                position = 0
            }
        } while (position <= 17)

    }

    private fun isRoundAnswer(position: Int): Boolean {
        return (position < 17
                && buttonsMutableList[position].value!![0] == buttonsMutableList[18].value!![0]
                && buttonsMutableList[position + 1].value!![0] == buttonsMutableList[19].value!![0])
    }

    fun checkChosenOptions() {
        if (round > 0) {
            rateResponses()
        }
        if (round >= ROUNDS_NUMBER) {
            Rfn005Repository.initFirebase(context)
            if (!idUser.isNullOrEmpty()) {

                Rfn005Repository.sendResults(
                    idUser!!, Rfn005Results(
                        respuestasCorrectas = score,
                        respuestasOmitidas = omittedAnswers,
                        respuestasIncorrectas = wrongAnswers,
                        tiempo = Date().time - startDate.time
                    )
                )
            }
            navigator?.toNextActvity()
        }
        generateRamdonDirections()
    }

    private fun rateResponses() {
        var selectedAnswers = 0
        val actuallyScore = score
        buttonsMutableList.forEachIndexed { index, mutableLiveData ->
            if (isRoundAnswer(index) && index < 17 && buttonsMutableList[index + 1].value!![1] == 1) {
                score++
            } else if (isRoundAnswer(index) && index < 17 && buttonsMutableList[index + 1].value!![1] == 0) {
                omittedAnswers++
            }
            if (mutableLiveData.value!![1] == 1)
                selectedAnswers++
        }
        wrongAnswers += (selectedAnswers - (score - actuallyScore))
        points.value = score
    }

}