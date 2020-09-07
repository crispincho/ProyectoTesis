package com.umb.myapplication.features.formulario.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umb.myapplication.features.formulario.data.entities.User
import java.util.*

object FormularioRepository {

    val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context){
        FirebaseApp.initializeApp(context)
    }

    fun insertUser(user:User){
        databaseReference.child("participantes").child(user.id).setValue(user)
    }
}