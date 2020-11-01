package com.umb.myapplication.features.rfn005.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.rfn005.data.entities.Rfn005Results

object Rfn005Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun sendResults(idUser: String, results: Rfn005Results, guardianUser: String, sampleCode: String) {
        databaseReference.child("participants").child(idUser).child("rfn005")
            .setValue(results)
        databaseReference.child("results").child(guardianUser).child(sampleCode)
            .child("rfn005").setValue(results)
    }

}