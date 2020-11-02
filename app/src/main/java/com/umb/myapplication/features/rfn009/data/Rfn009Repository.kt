package com.umb.myapplication.features.rfn009.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.rfn009.data.entities.Rfn009Result

object Rfn009Repository {
    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context){
        FirebaseApp.initializeApp(context)
    }

    fun insertResultTestRFN009(idUser: String, score: Int, time:Long, resUserText:MutableList<String>
                               , guardianUser: String, sampleCode: String){
        databaseReference.child("participants").child(idUser).child("rfn009")
            .setValue( Rfn009Result(score, time,resUserText ))

        databaseReference.child("results").child(guardianUser).child(sampleCode)
            .child("rfn009").child(idUser).setValue(Rfn009Result(score, time,resUserText ))
    }
}