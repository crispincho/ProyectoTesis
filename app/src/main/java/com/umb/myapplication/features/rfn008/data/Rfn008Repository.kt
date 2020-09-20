package com.umb.myapplication.features.rfn008.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.rfn008.data.entities.Rfn008Result

object Rfn008Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun sendData(userID: String, time: Long, score: Int) {
        databaseReference.child("participantes").child(userID).child("Results")
            .child("RFN008").setValue(Rfn008Result(score, time))
    }

}