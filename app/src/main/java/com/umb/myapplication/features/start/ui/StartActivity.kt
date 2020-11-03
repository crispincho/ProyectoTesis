package com.umb.myapplication.features.start.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.umb.myapplication.R
import com.umb.myapplication.core.ui.GameActivity
import com.umb.myapplication.features.rfn004.ui.Rfn004Activity

class StartActivity : GameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val idUser = intent.getStringExtra(Companion.ID_USER).toString()
        val guardianUser = intent.getStringExtra(Companion.GUARDIAN_USER).toString()
        val sampleCode = intent.getStringExtra(Companion.SAMPLE_CODE).toString()
        findViewById<Button>(R.id.btnStart).setOnClickListener {
            val intent = Intent(this, Rfn004Activity::class.java)
            intent.putExtra(Companion.ID_USER, idUser)
            intent.putExtra(Companion.GUARDIAN_USER, guardianUser)
            intent.putExtra(Companion.SAMPLE_CODE, sampleCode)
            startActivity(intent)
        }
    }
}