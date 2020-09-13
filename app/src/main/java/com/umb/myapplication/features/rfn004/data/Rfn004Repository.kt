package com.umb.myapplication.features.rfn004.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.formulario.data.entities.User
import com.umb.myapplication.features.rfn004.data.enteties.Rfn004Result
import com.umb.myapplication.features.rfn006.data.entities.Rfn006Result
import java.util.*

object Rfn004Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context){
        FirebaseApp.initializeApp(context)
    }

    fun insertResultTestRFN004(userID: String, score: Int, time:Long){
        databaseReference.child("participantes").child(userID).child("Results").child("RFN004").setValue(
            Rfn004Result(score, time)
        )
    }
}