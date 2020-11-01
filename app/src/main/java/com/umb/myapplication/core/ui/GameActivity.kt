package com.umb.myapplication.core.ui

import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.umb.myapplication.R

open class GameActivity : AppCompatActivity() {

    companion object {
        const val ID_USER = "idUser"
        const val SAMPLE_CODE = "sampleCode"
        const val GUARDIAN_USER = "guardianUser"
        const val REQUEST_RECORD_AUDIO = 1001
    }

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()
        window.decorView.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            validatePermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    open fun validatePermission() {
        if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_RECORD_AUDIO) {
            validatePermission()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}