package com.umb.myapplication.features.formulario.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.features.formulario.data.FormularioRepository
import com.umb.myapplication.features.formulario.data.entities.User
import com.umb.myapplication.features.formulario.ui.FormularioNavigator



class FormularioViewmodel(application: Application, val context: Context) :
    AndroidViewModel(application) {
    var navigator: FormularioNavigator? =null
    val nombre = MutableLiveData<String>()
    val isVisibleName = MutableLiveData<Boolean>()
    val edad = MutableLiveData<String>()
    val isVisibleAge = MutableLiveData<Boolean>()
    val correo = MutableLiveData<String>()
    val isVisibleEmail = MutableLiveData<Boolean>()
    val grado = MutableLiveData<String>()
    val isVisibleCourse = MutableLiveData<Boolean>()
    val telefono = MutableLiveData<String>()
    val isVisibleTelephone = MutableLiveData<Boolean>()
    val codigoGuardian = MutableLiveData<String>()
    val isVisibleGuardian = MutableLiveData<Boolean>()
    val parentesco = MutableLiveData<String>()
    val isVisibleRelationship = MutableLiveData<Boolean>()

    fun toFirstTest() {
//        FormularioRepository.initFirebase(context)
//        isVisibleName.value = nombre.value.isNullOrEmpty()
//        isVisibleAge.value = edad.value.isNullOrEmpty()
//        isVisibleEmail.value = correo.value.isNullOrEmpty()
//        isVisibleCourse.value = grado.value.isNullOrEmpty()
//        isVisibleTelephone.value = telefono.value.isNullOrEmpty()
//        isVisibleGuardian.value = codigoGuardian.value.isNullOrEmpty()
//        isVisibleRelationship.value = parentesco.value.isNullOrEmpty()
//        if (!nombre.value.isNullOrEmpty() && !edad.value.isNullOrEmpty() && !correo.value.isNullOrEmpty() && !grado.value.isNullOrEmpty() && !telefono.value.isNullOrEmpty() && !codigoGuardian.value.isNullOrEmpty() && !parentesco.value.isNullOrEmpty()) {
//            val user = User(
//                name = nombre.value!!,
//                age = edad.value!!.toInt(),
//                email = correo.value!!,
//                course = grado.value!!,
//                telephone = telefono.value!!,
//                guardianName = codigoGuardian.value!!,
//                relationship = parentesco.value!!
//            )
//            FormularioRepository.insertUser(user)
//

            navigator?.toNextActvity(idUser = 132.toString())
//        }


    }



}