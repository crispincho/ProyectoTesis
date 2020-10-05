package com.umb.myapplication.features.rfn009.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.core.ui.GameActivity
import com.umb.myapplication.databinding.ActivityRfn009Binding
import com.umb.myapplication.features.rfn009.ui.viewmodel.Rfn009ViewModel
import com.umb.myapplication.features.rfn009.ui.viewmodel.Rfn009ViewModelFactory

class Rfn009Activity : GameActivity(), Rfn009Navigator {

    lateinit var binding: ActivityRfn009Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rfn009)
        val factory = Rfn009ViewModelFactory(application, this)
        val viewmodel = ViewModelProvider(this, factory).get(Rfn009ViewModel::class.java)
        viewmodel.navigator = this
        viewmodel.idUser = intent.getStringExtra("idUser") ?: ""
        viewmodel.score.observe(this, Observer {
            // TODO: 4/10/2020 need do a link with the layout view
        })
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        activateView()
    }

    private fun activateView() {

    }

    override fun toNextActvity() {
        TODO("Not yet implemented")
    }
}