package com.umb.myapplication.features.rfn008.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityRfn008Binding
import com.umb.myapplication.features.rfn008.ui.viewmodel.Rfn008ViewModel
import com.umb.myapplication.features.rfn008.ui.viewmodel.Rfn008ViewModelFactory
import kotlinx.android.synthetic.main.status_bar.view.*

class Rfn008Activity : AppCompatActivity(), Rfn008Navigator {

    private lateinit var binding: ActivityRfn008Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rfn008)
        val factory = Rfn008ViewModelFactory(application, this)
        val viewmodel = ViewModelProvider(this, factory).get(Rfn008ViewModel::class.java)
        viewmodel.navigator = this
        viewmodel.idUser = intent.getStringExtra("idUser")
        viewmodel.score.observe(this, Observer {
            binding.statusBar.tvPoints.text = it.toString()
        })
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
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

    }

    override fun toNextActvity() {
        TODO("Not yet implemented")
    }

    override fun activateViews() {

    }
}