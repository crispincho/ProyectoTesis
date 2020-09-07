package com.umb.myapplication.features.rfn004.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.formulario.data.entities.User

object Rfn004Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context){
        FirebaseApp.initializeApp(context)
    }

    fun insertResultTestRFN004(userID: String, puntuacion: Int){
        databaseReference.child("participantes").child(userID).child("Results").child("RFN004").setValue(puntuacion)
    }
}