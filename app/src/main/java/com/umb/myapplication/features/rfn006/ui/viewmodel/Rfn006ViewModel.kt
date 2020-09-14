package com.umb.myapplication.features.rfn006.ui.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.R
import com.umb.myapplication.features.rfn006.data.Rfn006Repository
import com.umb.myapplication.features.rfn006.data.entities.Word
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
    private lateinit var startTime: Date
    private var wordsIndex = 0

    private var listOfWords: List<String>
    private var isExistWordlist: List<Boolean>

    val firstText = MutableLiveData<String>()
    val showFirstText = MutableLiveData<Boolean>()
    val secondText = MutableLiveData<String>()
    val showSecondText = MutableLiveData<Boolean>()
    val threethText = MutableLiveData<String>()
    val showThreethText = MutableLiveData<Boolean>()

    private val listWordResult = mutableListOf<Word>()

    init {
        Rfn006Repository.initFirebase(context)
        val words = mutableListOf<String>()
        val answers = mutableListOf<Boolean>()
        Rfn006Repository.getDataToTest().forEach {
            words.add(it.key)
            answers.add(it.value)
        }
        listOfWords = words
        isExistWordlist = answers
        val mediaPlayer = MediaPlayer.create(context, R.raw.rfn006_instrucciones)
        mediaPlayer.start()
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Thread.sleep(mediaPlayer.duration.toLong())
            }
            navigator?.activateViews()
            startTime = Date()
        }
        score.value = 0
        firstText.value = listOfWords[wordsIndex++]
        secondText.value = listOfWords[wordsIndex++]
        threethText.value = listOfWords[wordsIndex++]
        showFirstText.value = true
        showSecondText.value = true
        showThreethText.value = true
    }

    fun selectSide(element: Int) {
        if (!::startTime.isInitialized)
            return
        val actualIndex = when (element) {
            1, 4 -> listOfWords.indexOf(firstText.value)
            2, 5 -> listOfWords.indexOf(secondText.value)
            3, 6 -> listOfWords.indexOf(threethText.value)
            else -> 0
        }
        when (element) {
            1, 2, 3 -> {
                if (actualIndex < listOfWords.size && isExistWordlist[actualIndex]) {
                    score.value = 1 + score.value!!
                    listWordResult.add(Word(description = listOfWords[actualIndex], answer = true))
                } else {
                    listWordResult.add(Word(description = listOfWords[actualIndex], answer = false))
                }
                navigator?.slideView(element, false, element, ::nextWord)
            }
            4, 5, 6 -> {
                if (actualIndex < listOfWords.size && !isExistWordlist[actualIndex]) {
                    score.value = 1 + score.value!!
                    listWordResult.add(Word(description = listOfWords[actualIndex], answer = true))
                } else {
                    listWordResult.add(Word(description = listOfWords[actualIndex], answer = false))
                }
                navigator?.slideView(element, true, element - 3, ::nextWord)
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
            Rfn006Repository.sendRestults(
                idUser!!,
                Date().time - startTime.time,
                score.value!!,
                listWordResult
            )
            navigator?.toNextActvity()
        }
    }

}