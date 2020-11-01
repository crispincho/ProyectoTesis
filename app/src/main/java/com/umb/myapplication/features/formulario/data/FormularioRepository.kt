package com.umb.myapplication.features.formulario.data

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.umb.myapplication.features.formulario.data.entities.Guardian
import com.umb.myapplication.features.formulario.data.entities.User
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodel

object FormularioRepository {

    const val TAG = "FormularioRepository"

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun insertUser(user: User) {
        databaseReference.child("participants").child(user.id).setValue(user)
    }

    fun getGuardianByCode(
        code: String?,
        callback: FormularioViewmodel.ViewModelRequest
    ) {
        if (code == null) {
            callback.gotNoAnswer("El campo codigo esta vacio")
            return
        }
        databaseReference.child("sampleCodes").child(code).child("guardianUser")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    val msg = "Error al obtener guardianes ${error.code}: ${error.message}"
                    Log.i(TAG, msg)
                    callback.gotNoAnswer(msg)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userName = snapshot.getValue(String::class.javaObjectType)
                    if (!userName.isNullOrEmpty()) {
                        callback.gotAnswer(
                            Guardian(
                                sampleCode = code,
                                guardianUser = userName
                            )
                        )
                    } else {
                        callback.gotNoAnswer("El código introducido no existe, por favor verifique el código")
                    }
                }

            })
    }
}