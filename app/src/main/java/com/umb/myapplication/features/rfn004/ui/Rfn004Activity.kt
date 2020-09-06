package com.umb.myapplication.features.rfn004.ui

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityRfn004Binding
import com.umb.myapplication.features.rfn004.ui.viewmodel.Rfn004ViewModel
import com.umb.myapplication.features.rfn004.ui.viewmodel.Rfn004ViewmodelFactory

class Rfn004Activity : AppCompatActivity() {
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=DataBindingUtil.setContentView<ActivityRfn004Binding>(this,R.layout.activity_rfn004)
        val factory=Rfn004ViewmodelFactory(application, this)
        binding.viewmodel=factory.create(Rfn004ViewModel::class.java)
        window.decorView.apply { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            systemUiVisibility=View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY  or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        }
    }
}