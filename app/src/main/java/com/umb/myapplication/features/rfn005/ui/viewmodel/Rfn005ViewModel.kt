package com.umb.myapplication.features.rfn005.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.umb.myapplication.features.rfn005.data.Rfn005Repository
import com.umb.myapplication.features.rfn005.data.entities.Rfn005Results
import com.umb.myapplication.features.rfn005.ui.Rfn005Navigator
import kotlinx.coroutines.Dispatchers
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
    var guardianUser: String? = null
    var sampleCode: String? = null

    lateinit var startDate: Date

    private var score = 0
    private var wrongAnswers = 0
    private var omittedAnswers = 0

    val points = MutableLiveData<Int>()

    //Equivalencias direcciones arriba = 0 , derecha = 1 , izquierda = 2 , abajo = 3
    val buttonsMutableList = mutableListOf<MutableList<Int?>>()

    init {
        for (i in 0..20) {
            buttonsMutableList.add(mutableListOf(0,0))
        }
    }

    private fun generateRamdonDirections() {
        viewModelScope.launch(Dispatchers.Default) {
            round++
            val randomPositions = mutableListOf<Int>()
            for (i in 0..19) {
                randomPositions.add(i)
            }
            randomPositions[19] = (0..3).random()
            do {
                randomPositions[18] = (0..3).random()
            } while (randomPositions[18] == randomPositions[19])
            var position = 0
            var answers = 0
            do {
                randomPositions[position] = (0..3).random()
                if (position < 17 && position != 8 && answers < ROUND_ANSWERS && randomPositions[position] == randomPositions[18]) {
                    position++
                    answers++
                    randomPositions[position] = randomPositions[19]
                } else if (answers == ROUND_ANSWERS && randomPositions[position] == randomPositions[18]) {
                    do {
                        randomPositions[position + 1] = (0..3).random()
                    } while (isRoundAnswer(randomPositions, position))
                    position++
                }
                position++
                if (position == 17 && answers < 3) {
                    position = 0
                    answers = 0
                }
            } while (position <= 17)
            randomPositions.forEachIndexed { index, i ->
                withContext(Dispatchers.Main) {
                    buttonsMutableList[index] = mutableListOf(i, 0)
                    navigator?.rotateButton(index,i)
                }
            }
        }
    }

    private fun isRoundAnswer(ramdomPosition: List<Int>, position: Int): Boolean {
        return (position < 17
                && ramdomPosition[position] == ramdomPosition[18]
                && ramdomPosition[position + 1] == ramdomPosition[19]
                && position != 8)
    }

    fun checkChosenOptions() {
        viewModelScope.launch(Dispatchers.Default) {
            if (round > 0) {
                rateResponses()
            }
            if (round >= ROUNDS_NUMBER) {
                Rfn005Repository.initFirebase(context)
                if (!idUser.isNullOrEmpty()) {
                    Rfn005Repository.sendResults(
                        idUser!!, Rfn005Results(
                            correctAnswers = score,
                            omitedAnswers = omittedAnswers,
                            wrongAnswers = wrongAnswers,
                            time = Date().time - startDate.time
                        ),
                        guardianUser.toString(),
                        sampleCode.toString()
                    )
                }
                withContext(Dispatchers.Main) {
                    navigator?.toNextActvity()
                }
            }
            generateRamdonDirections()
        }
    }

    private fun rateResponses() {
        viewModelScope.launch {
            var selectedAnswers = 0
            val actuallyScore = score
            val randomPositions = mutableListOf<Int>()
            buttonsMutableList.forEach {
                randomPositions.add(it[0]!!)
            }
            buttonsMutableList.forEachIndexed { index, mutableLiveData ->
                if (isRoundAnswer(
                        randomPositions,
                        index
                    ) && index < 17 && buttonsMutableList[index + 1][1] == 1
                ) {
                    score++
                } else if (isRoundAnswer(
                        randomPositions,
                        index
                    ) && index < 17 && buttonsMutableList[index + 1][1] == 0
                ) {
                    omittedAnswers++
                }
                if (mutableLiveData[1] == 1)
                    selectedAnswers++
            }
            wrongAnswers += (selectedAnswers - (score - actuallyScore))
            withContext(Dispatchers.Main) {
                points.value = score
            }
        }
    }

}