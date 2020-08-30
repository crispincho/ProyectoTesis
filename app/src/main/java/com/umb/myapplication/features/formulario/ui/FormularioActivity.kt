package com.umb.myapplication.features.formulario.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityFormularioBinding
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodel
import com.umb.myapplication.features.formulario.ui.viewmodel.FormularioViewmodelFactory

class FormularioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFormularioBinding>(
            this,
            R.layout.activity_formulario
        )
        val factory = FormularioViewmodelFactory(application, this)
        binding.viewmodel = ViewModelProvider(this,factory).get(FormularioViewmodel::class.java)
        binding.lifecycleOwner = this
    }
}