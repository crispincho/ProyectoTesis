package com.umb.myapplication.core.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.umb.myapplication.R
import com.umb.myapplication.features.formulario.ui.FormularioActivity

class SplashActivity : GameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun validatePermission() {
        if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, FormularioActivity::class.java)
                startActivity(intent)
                finish()
            }, 3500)
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(
                    this,
                    R.string.recod_audio_permission_denied,
                    Toast.LENGTH_SHORT
                ).show()
            }
            requestPermissions(
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO
            )
        }
    }

}