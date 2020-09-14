package com.umb.myapplication.features.rfn004.ui


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.databinding.DataBindingUtil
import com.umb.myapplication.R
import com.umb.myapplication.databinding.ActivityRfn004Binding
import com.umb.myapplication.features.rfn004.ui.viewmodel.Rfn004ViewModel
import com.umb.myapplication.features.rfn004.ui.viewmodel.Rfn004ViewmodelFactory
import com.umb.myapplication.features.rfn005.ui.Rfn005Activity
import kotlinx.android.synthetic.main.status_bar.view.*
import androidx.lifecycle.Observer

class Rfn004Activity : AppCompatActivity() , Rfn004Navigator {
    lateinit var binding: ActivityRfn004Binding
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView<ActivityRfn004Binding>(this,R.layout.activity_rfn004)
        val factory=Rfn004ViewmodelFactory(application, this)
        val viewmodel = factory.create(Rfn004ViewModel::class.java)
        binding.buttonEquals.isEnabled=false
        binding.buttonDifferent.isEnabled=false
        viewmodel.navigator = this
        viewmodel.puntos.observe(this, Observer {
            binding.statusBar.tvPoints.text = it
        })
        binding.viewmodel = viewmodel
        window.decorView.apply { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            systemUiVisibility=View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY  or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        }

    }
    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()
        window.decorView.apply {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                systemUiVisibility=View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }

        }
    }

    override fun getUserID() : String {
        return intent.getStringExtra("idUser")!!
    }

    override fun toNextActvity(idUser: String) {
        val intent = Intent(this,Rfn005Activity::class.java )
        intent.putExtra("idUser", idUser)
        startActivity(intent)
    }
    override fun activateTest() {
        binding.buttonDifferent.isEnabled=true
        binding.buttonEquals.isEnabled=true
        binding.statusBar.tvTime.base= SystemClock.elapsedRealtime()
        binding.statusBar.tvTime.start()
    }
}