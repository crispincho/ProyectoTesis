package com.umb.myapplication.features.rfn004.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.rfn004.data.enteties.Rfn004Result


object Rfn004Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context){
        FirebaseApp.initializeApp(context)
    }

    //sampleCode - codigoMuestral - agrupacion de estudio

    fun insertResultTestRFN004(idUser: String, guardianUser: String, sampleCode: String, score: Int, time:Long){
        databaseReference.child("participants").child(idUser).child("rfn004").setValue(
            Rfn004Result(score, time))
        databaseReference.child("results").child(guardianUser).child(sampleCode).child("rfn004").setValue(
            Rfn004Result(score, time)
        )
    }
}