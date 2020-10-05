package com.umb.myapplication.features.formulario.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityFormularioBinding
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodel
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodelFactory
import com.umb.myapplication.features.rfn004.ui.Rfn004Activity

class FormularioActivity : AppCompatActivity(), FormularioNavigator {

    private val REQUEST_RECOD_AUDIO = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFormularioBinding>(
            this,
            R.layout.activity_formulario
        )
        val factory = FormularioViewmodelFactory(application, this)
        val viewmodel = ViewModelProvider(this, factory).get(FormularioViewmodel::class.java)
        viewmodel.navigator = this
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            validatePermission()
        }
    }

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()
        window.decorView.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }
    }

    override fun toNextActvity(idUser: String) {
        val intent = Intent(this, Rfn004Activity::class.java)
        intent.putExtra("idUser", idUser)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun validatePermission() {
        if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Log.i("Prueba", "Tiene permiso")
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(
                    this,
                    "Permiso del microfono es necesarioa para el uso de la aplicacion",
                    Toast.LENGTH_SHORT
                ).show()
            }
            requestPermissions(
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_RECOD_AUDIO
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_RECOD_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("Prueba", "Tiene permiso")
            } else {
                Toast.makeText(
                    this,
                    "Permiso del microfono es necesarioa para el uso de la aplicacion",
                    Toast.LENGTH_SHORT
                ).show()
            }
            requestPermissions(
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_RECOD_AUDIO
            )
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}