package com.umb.myapplication.features.rfn006.ui.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.view.View
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
    private var wordsIndex = 0
    private val startTime = Date()

    private val listOfWords = listOf(
        "Palabra1",
        "Palabra2",
        "Palabra3",
        "Palabra4",
        "Palabra5",
        "Palabra6",
        "Palabra7",
        "Palabra8",
        "Palabra9",
        "Palabra10"
    )
    private val isExistWordlist = listOf(
        true,
        false,
        false,
        true,
        false,
        true,
        true,
        true,
        false,
        true
    )

    val firstText = MutableLiveData<String>()
    val showFirstText = MutableLiveData<Boolean>()
    val secondText = MutableLiveData<String>()
    val showSecondText = MutableLiveData<Boolean>()
    val threethText = MutableLiveData<String>()
    val showThreethText = MutableLiveData<Boolean>()

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
        score.value = 0
        firstText.value = listOfWords[wordsIndex++]
        secondText.value = listOfWords[wordsIndex++]
        threethText.value = listOfWords[wordsIndex++]
        showFirstText.value = true
        showSecondText.value = true
        showThreethText.value = true
    }

    fun selectSide(arrow: View, text: View, element: Int) {
        val actualIndex = when (element) {
            1, 4 -> listOfWords.indexOf(firstText.value)
            2, 5 -> listOfWords.indexOf(secondText.value)
            3, 6 -> listOfWords.indexOf(threethText.value)
            else -> 0
        }
        when (element) {
            1, 2, 3 -> {
                if (actualIndex < listOfWords.size && isExistWordlist[actualIndex])
                    score.value = 1 + score.value!!
                navigator?.slideView(arrow, text, false, element, ::nextWord)
            }
            4, 5, 6 -> {
                if (actualIndex < listOfWords.size && !isExistWordlist[actualIndex])
                    score.value = 1 + score.value!!
                navigator?.slideView(arrow, text, true, element - 3, ::nextWord)
            }
        }
    }

    private fun nextWord(element: Int) {
        when (element) {
            1 -> {
                if (wordsIndex < listOfWords.size)
                    firstText.value = listOfWords[wordsIndex++]
                else
                    showFirstText.value = false
            }
            2 -> {
                if (wordsIndex < listOfWords.size)
                    secondText.value = listOfWords[wordsIndex++]
                else
                    showSecondText.value = false
            }
            3 -> {
                if (wordsIndex < listOfWords.size)
                    threethText.value = listOfWords[wordsIndex++]
                else
                    showThreethText.value = false
            }
        }
        if (showFirstText.value == false && showSecondText.value == false && showThreethText.value == false) {
            navigator?.goToNextTest()
        }
    }

}