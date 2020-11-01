package com.umb.myapplication.features.rfn004.ui


import android.content.Intent
import android.os.Build
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
import com.umb.myapplication.core.ui.GameActivity
import com.umb.myapplication.core.utils.DialogInstructions
import com.umb.myapplication.core.utils.DialogInstructionsListener

class Rfn004Activity : GameActivity(), Rfn004Navigator {
    lateinit var binding: ActivityRfn004Binding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityRfn004Binding>(this, R.layout.activity_rfn004)
        val factory = Rfn004ViewmodelFactory(application, this)
        val viewmodel = factory.create(Rfn004ViewModel::class.java)
        binding.buttonEquals.isEnabled = false
        binding.buttonDifferent.isEnabled = false
        viewmodel.navigator = this
        viewmodel.idUser = intent.getStringExtra(Companion.ID_USER).toString()
        viewmodel.guardianCode = intent.getStringExtra(Companion.GUARDIAN_CODE).toString()
        viewmodel.sampleCode = intent.getStringExtra(Companion.SAMPLE_CODE).toString()
        viewmodel.puntos.observe(this, Observer {
            binding.statusBar.tvPoints.text = it
        })
        binding.viewmodel = viewmodel
        window.decorView.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
        activateViews()
    }

    override fun getUserID(): String {
        return intent.getStringExtra(Companion.ID_USER)!!
    }

    override fun toNextActvity(idUser: String, guardianCode: String, sampleCode: String) {
        val intent = Intent(this, Rfn005Activity::class.java)
        intent.putExtra(Companion.ID_USER, idUser)
        intent.putExtra(Companion.GUARDIAN_CODE, guardianCode)
        intent.putExtra(Companion.SAMPLE_CODE, sampleCode)
        startActivity(intent)
    }

    override fun activateTest() {
        binding.buttonDifferent.isEnabled = true
        binding.buttonEquals.isEnabled = true
        binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
        binding.statusBar.tvTime.start()
    }

    fun activateViews() {
        val dialog = DialogInstructions(
            this,
            getString(R.string.test_rfn004_instructions),
            R.raw.rfn004_instructivo,
            object : DialogInstructionsListener {
                override fun dismmissDialog() {
                    binding.viewmodel?.playInitSound()
                }
            })
        dialog.show()
    }
}