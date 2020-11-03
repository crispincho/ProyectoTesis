package com.umb.myapplication.features.rfn009.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.features.rfn009.data.Rfn009Repository.insertResultTestRFN009
import com.umb.myapplication.features.rfn009.ui.Rfn009Navigator
import java.util.*

class Rfn009ViewModel(application: Application, val context: Context) :
    AndroidViewModel(application) {

    lateinit var navigator: Rfn009Navigator
    var idUser: String? = null
    var guardianUser: String? = null
    var sampleCode: String? = null

    val score = MutableLiveData<Int>()

    private var indexWork: Int = 0

    private var dateIninitial: Date = Date()
    private val listWorks = listOf("La mayor de las niñas era Rosa",
        "La maestra nos enseñó unas poesias",
        "Mi mamá me hice un rico pastel",
        "Tuve que hacer mañana mi tarea",
        "Mariana tiene ocho años",
        "Tengo muchas dulces en mi bolsillo",
        "Mis juguetes preferidos son los carritos",
        "La gallina y sus pollitos comen granos de maíz",
        "Mañana fui al cine con mí tía")
    private val listWorksCorrects = listOf("La mayor de las niñas era Rosa",
        "La maestra nos enseñó unas poesias",
        "Mi mamá me hizo un rico pastel",
        "Tengo que hacer mañana mi tarea",
        "Mariana tiene ocho años",
        "Tengo muchos dulces en mi bolsillo",
        "Mis juguetes preferidos son los carritos",
        "La gallina y sus pollitos comen granos de maíz",
        "Mañana voy al cine con mí tía")
    private var resUserText: MutableList<String> = mutableListOf()
    private var resUserBoolean: MutableList<Boolean> = mutableListOf()
    init {
        score.value=0
    }

    fun actionCorrect(work: TextView, correctWork: EditText){
        Log.d("index", indexWork.toString())
        Log.d("listWorks", listWorks[indexWork])
        Log.d("listWorksCorrects", listWorksCorrects[indexWork])
        //
        if (correctWork.text != null && correctWork.text.toString() != ""){
            Log.d("index", "ingreso a if")
             resUserText.add(correctWork.text.toString())
             if (correctWork.text.toString().equals(listWorksCorrects[indexWork], ignoreCase = true)){
                 resUserBoolean.add(true)
                 score.value = score.value!!+1
             }else{
                resUserBoolean.add(false)
             }
        }else{
            Log.d("index", "ingreso a else")
            resUserText.add("null")
            if (work.text.toString() == listWorksCorrects[indexWork]){
                resUserBoolean.add(true)
                score.value = score.value!!+1
            }else{
                resUserBoolean.add(false)
            }
        }
        correctWork.setText("")
        correctWork.visibility = View.INVISIBLE

        if(listWorks.size==indexWork+1){
            insertResultTestRFN009(idUser.toString(), score.value!!.toInt(),
                                Date().time-dateIninitial.time, resUserText,
                                    guardianUser.toString(),
                                    sampleCode.toString())
           navigator.toNextActvity()
        }else{
            indexWork++
            work.text = listWorks[indexWork]
        }


    }

    fun actionIncorrect(correctWork: EditText){
        if (correctWork.visibility==View.INVISIBLE){
            correctWork.visibility = View.VISIBLE
            correctWork.setText("")
        }else{
            correctWork.visibility = View.INVISIBLE
            correctWork.setText("")
        }
    }
    fun initWork(work: TextView){
        work.text = listWorks[indexWork]
    }

}