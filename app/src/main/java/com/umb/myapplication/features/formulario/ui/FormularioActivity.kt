package com.umb.myapplication.features.formulario.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityFormularioBinding
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodel
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodelFactory
import com.umb.myapplication.features.rfn004.ui.Rfn004Activity

class FormularioActivity : AppCompatActivity(), FormularioNavigator {
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
}