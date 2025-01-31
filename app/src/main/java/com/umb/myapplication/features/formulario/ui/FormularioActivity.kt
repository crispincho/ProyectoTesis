package com.umb.myapplication.features.formulario.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.core.ui.GameActivity
import com.umb.myapplication.databinding.ActivityFormularioBinding
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodel
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodelFactory
import com.umb.myapplication.features.start.ui.StartActivity

class FormularioActivity : GameActivity(), FormularioNavigator {


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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P)
            binding.animationRobot.speed = 0F
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

    override fun toNextActvity(
        idUser: String,
        sampleCode: String,
        guardianUser: String
    ) {
        val intent = Intent(this, StartActivity::class.java)
        intent.putExtra(Companion.ID_USER, idUser)
        intent.putExtra(Companion.GUARDIAN_USER, guardianUser)
        intent.putExtra(Companion.SAMPLE_CODE, sampleCode)
        startActivity(intent)
    }


}