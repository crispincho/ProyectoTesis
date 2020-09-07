package com.umb.myapplication.features.rfn005.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.rfn005.data.entities.Rfn005Results

object Rfn005Repository {

    val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun sendResults(idUser: String, results: Rfn005Results) {
        databaseReference.child("participantes").child(idUser).child("Results").child("RFN005")
            .setValue(results)
    }

}