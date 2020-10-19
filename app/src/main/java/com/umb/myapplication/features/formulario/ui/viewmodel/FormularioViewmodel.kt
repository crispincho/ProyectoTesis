package com.umb.myapplication.features.formulario.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umb.myapplication.R
import com.umb.myapplication.features.formulario.data.FormularioRepository
import com.umb.myapplication.features.formulario.data.entities.Guardian
import com.umb.myapplication.features.formulario.data.entities.User
import com.umb.myapplication.features.formulario.ui.FormularioNavigator
import java.lang.NumberFormatException


class FormularioViewmodel(application: Application, val context: Context) :
    AndroidViewModel(application) {
    var navigator: FormularioNavigator? = null
    val nombre = MutableLiveData<String>()
    val isVisibleName = MutableLiveData<Boolean>()
    val nameError = MutableLiveData<String>()
    val edad = MutableLiveData<String>()
    val isVisibleAge = MutableLiveData<Boolean>()
    val ageError = MutableLiveData<String>()
    val correo = MutableLiveData<String>()
    val isVisibleEmail = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<String>()
    val grado = MutableLiveData<Int>()
    val isVisibleCourse = MutableLiveData<Boolean>()
    val gradeError = MutableLiveData<String>()
    val telefono = MutableLiveData<String>()
    val isVisiblePhone = MutableLiveData<Boolean>()
    val phoneError = MutableLiveData<String>()
    val codigoGuardian = MutableLiveData<String>()
    val isVisibleGuardian = MutableLiveData<Boolean>()
    val guardianError = MutableLiveData<String>()

    init {
        nombre.value = "Cristhian Rodriguez"
        edad.value = "1"
        correo.value = "crisdrodriguez@hotmail.com"
        grado.value = 2
        telefono.value = "3133470728"
        codigoGuardian.value = "JKL"
    }

    fun toFirstTest() {
        Toast.makeText(context, "spinnerIndex: ${grado.value}", Toast.LENGTH_SHORT).show()
        FormularioRepository.initFirebase(context)
        nameError.value = validateName(nombre.value)
        isVisibleName.value = nameError.value != ""
        ageError.value = validateAge(edad.value)
        isVisibleAge.value = ageError.value != ""
        emailError.value = validateEmailAddress(correo.value)
        isVisibleEmail.value = emailError.value != ""
        gradeError.value = validateGrade(grado.value!!)
        isVisibleCourse.value = gradeError.value != ""
        phoneError.value = validatePhone(telefono.value)
        isVisiblePhone.value = phoneError.value != ""
        val guardian = validateGuardianCode(codigoGuardian.value)
        guardianError.value = guardian.message
        isVisibleGuardian.value = guardianError.value != ""
        if (nameError.value != "" || ageError.value != "" || emailError.value != "" || gradeError.value != "" || phoneError.value != "" || guardianError.value != "")
            return
        val user = User(
            name = cleanName(nombre.value!!),
            age = edad.value!!.toInt(),
            email = correo.value!!,
            course = context.resources.getStringArray(R.array.grades)[grado.value!!],
            telephone = telefono.value!!,
            guardianName = codigoGuardian.value!!,
        )
        FormularioRepository.insertUser(user, guardian.code!!, guardian.sampleGroup!!.code)
        navigator?.toNextActvity(user.id, guardian.code, guardian.sampleGroup.code)
    }

    private fun validateName(name: String?): String {
        if (name.isNullOrEmpty()) {
            return context.getString(R.string.requerido)
        }
        val allowedCharacters = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚ "
        name.forEach {
            if (!allowedCharacters.contains(it.toUpperCase())) {
                return context.getString(R.string.nombre_invalido)
            }
        }
        return ""
    }

    private fun validateAge(ageString: String?): String {
        val MINIMUM_AGE = 6
        val MAXIMUN_AGE = 9
        if (ageString.isNullOrEmpty()) {
            return context.getString(R.string.requerido)
        }
        try {
            val age: Int = ageString.toInt()
            if (age < MINIMUM_AGE || age > MAXIMUN_AGE) {
                return context.getString(R.string.edad_fuera_rango)
            }
        } catch (e: NumberFormatException) {
            return context.getString(R.string.edad_invalida)
        }
        return ""
    }

    private fun validateEmailAddress(email: String?): String {
        if (email.isNullOrEmpty()) {
            return context.getString(R.string.requerido)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return context.getString(R.string.correo_invalido)
        }
        return ""
    }

    private fun validateGrade(index: Int): String {
        Toast.makeText(context, "spinner index: $index", Toast.LENGTH_SHORT).show()
        if (index <= 0) {
            return context.getString(R.string.requerido)
        }
        return ""
    }

    private fun validatePhone(phone: String?): String {
        if (phone.isNullOrEmpty()) {
            return context.getString(R.string.requerido)
        }
        if (phone.length != 10) {
            return context.getString(R.string.celular_invalido)
        }
        return ""
    }

    private fun validateGuardianCode(code: String?): Guardian {
        if (code.isNullOrEmpty()) {
            return Guardian(message = context.getString(R.string.requerido))
        }
        val guardian = getGuardianData(code)
        if (guardian.code == null || guardian.sampleGroup == null)
            return Guardian(message = context.getString(R.string.codigo_guardian_invalido))
        return guardian
    }

    private fun cleanName(name: String): String {
        var newName = name
        do {
            newName = newName.replace("  ", " ")
        } while (newName.contains("  "))
        return newName
    }

    private fun getGuardianData(code: String): Guardian {
        return FormularioRepository.getGuardianByCode(code) ?: Guardian()
    }
}