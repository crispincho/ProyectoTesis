package com.umb.myapplication.features.rfn008.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.core.utils.DialogInstructions
import com.umb.myapplication.core.utils.DialogInstructionsListener
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
        activateViews()
    }

    fun activateViews() {
        val dialog = DialogInstructions(
            this,
            "instrucciones probicionales",
            R.raw.rfn008_instrucciones,
            object : DialogInstructionsListener {
                override fun dismmissDialog() {
                    binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
                    binding.statusBar.tvTime.start()
                    binding.viewmodel?.validateSerie()
                }
            })
        dialog.show()
    }

    override fun toNextActvity() {
        Toast.makeText(this, "Fin de la prueba", Toast.LENGTH_LONG).show()
    }

    /**Animations**/
    override fun changeNumberBoxes(number: Int) {
        val fadedBox = ObjectAnimator.ofFloat(binding.layoutBox, "alpha", 1f, 0f)
        fadedBox.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                binding.viewmodel?.cleanBoxes()
                binding.viewmodel?.showBoxOne?.value = false
                binding.viewmodel?.showBoxTwo?.value = false
                binding.viewmodel?.showBoxThree?.value = false
                binding.viewmodel?.showBoxFour?.value = false
                binding.viewmodel?.showBoxFive?.value = false
                binding.viewmodel?.showBoxSix?.value = false
                binding.viewmodel?.showBoxSeven?.value = false
                if (number >= 1) {
                    binding.viewmodel?.showBoxOne?.value = true
                }
                if (number >= 2) {
                    binding.viewmodel?.showBoxTwo?.value = true
                }
                if (number >= 3) {
                    binding.viewmodel?.showBoxThree?.value = true
                }
                if (number >= 4) {
                    binding.viewmodel?.showBoxFour?.value = true
                }
                if (number >= 5) {
                    binding.viewmodel?.showBoxFive?.value = true
                }
                if (number >= 6) {
                    binding.viewmodel?.showBoxSix?.value = true
                }
                if (number >= 7) {
                    binding.viewmodel?.showBoxSeven?.value = true
                }
                val showBox = ObjectAnimator.ofFloat(binding.layoutBox, "alpha", 0f, 1f)
                showBox.apply {
                    duration = 500
                    start()
                }
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
        fadedBox.apply {
            duration = 500
            start()
        }
    }
}