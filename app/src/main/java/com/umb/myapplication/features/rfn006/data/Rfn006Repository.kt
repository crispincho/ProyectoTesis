package com.umb.myapplication.features.rfn006.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

object Rfn006Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun getDataToTest(): HashMap<String, Boolean> {
        val answerList = hashMapOf<String, Boolean>()
        databaseReference.child("Configuracion")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {

                }

            })
        return answerList
    }

}