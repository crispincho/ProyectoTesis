package com.umb.myapplication.features.formulario.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.features.formulario.data.FormularioRepository

class FormularioViewmodel(application: Application) : AndroidViewModel(application) {

    val nombre = MutableLiveData<String>()
    val edad = MutableLiveData<String>()
    val correo = MutableLiveData<String>()
    val grado = MutableLiveData<String>()
    val telefono = MutableLiveData<String>()
    val nombreAcudiente = MutableLiveData<String>()
    val parentesco = MutableLiveData<String>()

    fun toFirstTest() {

    }

}