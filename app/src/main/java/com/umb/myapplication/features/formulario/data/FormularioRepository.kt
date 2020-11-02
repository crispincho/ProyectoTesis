package com.umb.myapplication.features.formulario.data

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.umb.myapplication.features.formulario.data.entities.Guardian
import com.umb.myapplication.features.formulario.data.entities.SampleGroup
import com.umb.myapplication.features.formulario.data.entities.User
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodel

object FormularioRepository {

    const val TAG = "FormularioRepository"

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun insertUser(user: User, guardianCode: String, code: String) {
        databaseReference.child("participantes").child(guardianCode).child(code).child(user.id)
            .setValue(user)
    }

    fun getGuardianByCode(
        code: String?,
        callback: FormularioViewmodel.ViewModelRequest
    ) {
        if (code == null) {
            callback.gotNoAnswer()
            return
        }
        var guardianQuery: Guardian?
        databaseReference.child("guardian").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.i(
                    TAG,
                    "Error al obtener guardianes ${error.code}: ${error.message}"
                )
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var madeQueries = 0
                var isResult = false
                var group: SampleGroup? = null
                var totalQueries = 0
                snapshot.children.forEach { _ ->
                    totalQueries++
                }
                val guardians = snapshot.children.iterator()
                while (guardians.hasNext() && group == null) {
                    val guardian = guardians.next()
                    databaseReference.child("guardian")
                        .child(guardian.key!!)
                        .orderByChild("code").equalTo(code)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                Log.i(
                                    TAG,
                                    "Error en la consulta del codigo guardian ${error.code}: ${error.message}"
                                )
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                group = snapshot.getValue(SampleGroup::class.java)
                                if (group != null && guardian.key != null) {
                                    guardianQuery = Guardian(
                                        code = guardian.key!!,
                                        sampleGroup = group!!
                                    )
                                    isResult = true
                                    callback.gotAnswer(guardianQuery!!)
                                }
                                madeQueries++
                                if (!isResult && madeQueries >= totalQueries) {
                                    callback.gotNoAnswer()
                                }
                            }
                        })
                }
            }
        })
    }
}