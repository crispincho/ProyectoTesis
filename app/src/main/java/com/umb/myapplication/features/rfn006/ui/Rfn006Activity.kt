package com.umb.myapplication.features.rfn006.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityRfn006Binding
import com.umb.myapplication.features.rfn006.ui.viewmodel.Rfn006ViewModel
import com.umb.myapplication.features.rfn006.ui.viewmodel.Rfn006ViewModelFactory
import kotlinx.android.synthetic.main.status_bar.view.*

class Rfn006Activity : AppCompatActivity(), Rfn006Navigator {

    lateinit var binding: ActivityRfn006Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_rfn006
        )
        val factory = Rfn006ViewModelFactory(application, this)
        val viewmodel = ViewModelProvider(this, factory).get(Rfn006ViewModel::class.java)
        viewmodel.idUser = intent.getStringExtra("idUser")
        viewmodel.score.observe(this, Observer {
            binding.statusBar.tvPoints.text = it.toString()
        })
        binding.viewmodel = viewmodel
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

    }

    override fun activateViews() {
        binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
        binding.statusBar.tvTime.start()
    }
}