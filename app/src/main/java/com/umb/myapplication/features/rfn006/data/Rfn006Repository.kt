package com.umb.myapplication.features.rfn006.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.umb.myapplication.features.rfn006.data.entities.Rfn006Result
import com.umb.myapplication.features.rfn006.data.entities.Word

object Rfn006Repository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebase.reference

    fun initFirebase(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun getDataToTest(): HashMap<String, Boolean> {
        return getDafualtDataToTest()
    }

    private fun getDafualtDataToTest(): HashMap<String, Boolean> {
        val defaultWords = hashMapOf<String, Boolean>()
        defaultWords["abeja"] = true
        defaultWords["tarenje"] = false
        defaultWords["cadena"] = true
        defaultWords["petore"] = false
        defaultWords["tomipe"] = false
        defaultWords["petagoro"] = false
        defaultWords["boromsio"] = false
        defaultWords["panal"] = true
        defaultWords["resbaladilla"] = true
        defaultWords["gapila"] = false
        defaultWords["mofismo"] = false
        defaultWords["iguana"] = true
        defaultWords["mariposa"] = true
        defaultWords["fregadero"] = true
        defaultWords["reata"] = true
        defaultWords["sobre"] = true
        defaultWords["estropajo"] = true
        defaultWords["mablaxone"] = false
        defaultWords["onixis"] = false
        defaultWords["siramio"] = false
        defaultWords["intare"] = false
        defaultWords["queso"] = true
        defaultWords["cituñar"] = false
        defaultWords["jonatu"] = false
        defaultWords["hermano"] = true
        defaultWords["sajofatra"] = false
        defaultWords["pollo"] = true
        defaultWords["pacrifiso"] = false
        defaultWords["jugo"] = true
        defaultWords["meralo"] = false
        defaultWords["amplate"] = false
        defaultWords["tagra"] = false
        defaultWords["tasil"] = false
        defaultWords["vichalo"] = false
        defaultWords["minero"] = true
        defaultWords["termóstato"] = true
        defaultWords["número"] = true
        defaultWords["desarmador"] = true
        defaultWords["obrero"] = true
        defaultWords["critacion"] = false
        defaultWords["norago"] = false
        defaultWords["rellobo"] = false
        defaultWords["sardina"] = true
        defaultWords["jugo"] = true
        defaultWords["tortuga"] = true
        defaultWords["chayote"] = true
        defaultWords["tijane"] = false
        defaultWords["cohete"] = true
        defaultWords["helicóptero"] = true
        defaultWords["campesino"] = true
        return defaultWords
    }

    fun sendRestults(idUser: String, time: Long, score: Int, listWords: List<Word> , guardianUser: String, sampleCode: String) {
        databaseReference.child("participants").child(idUser).child("rfn006")
            .setValue(Rfn006Result(score, time, listWords))
        databaseReference.child("results").child(guardianUser).child(sampleCode)
            .child("rfn006").child(idUser).setValue(Rfn006Result(score, time, listWords))
    }

}