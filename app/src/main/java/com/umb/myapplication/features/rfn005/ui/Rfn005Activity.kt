package com.umb.myapplication.features.rfn005.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityRfn005Binding
import com.umb.myapplication.features.rfn005.ui.viewmodel.Rfn005ViewModel
import com.umb.myapplication.features.rfn005.ui.viewmodel.Rfn005ViewModelFactory

class Rfn005Activity : AppCompatActivity() {
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRfn005Binding>(
            this,
            R.layout.activity_rfn005
        )
        val factory = Rfn005ViewModelFactory(application, this)
        binding.viewmodel = factory.create(Rfn005ViewModel::class.java)
        window.decorView.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
    }
}