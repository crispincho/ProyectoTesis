package com.umb.myapplication.features.rfn007.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.rfn007.data.entities.Rfn007Result

object Rfn007Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context){
        FirebaseApp.initializeApp(context)
    }

    fun insertResultTestRFN007(idUser: String, score: Int, time:Long, guardianUser: String, sampleCode: String){
        databaseReference.child("participants").child(idUser).child("rfn007")
            .setValue(Rfn007Result(score, time))

        databaseReference.child("results").child(guardianUser).child(sampleCode).child("rfn007")
            .child(idUser).setValue(Rfn007Result(score, time))
    }
}