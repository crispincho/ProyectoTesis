package com.umb.myapplication.features.rfn006.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.core.ui.GameActivity
import com.umb.myapplication.core.utils.DialogInstructions
import com.umb.myapplication.core.utils.DialogInstructionsListener
import com.umb.myapplication.databinding.ActivityRfn006Binding
import com.umb.myapplication.features.rfn006.ui.viewmodel.Rfn006ViewModel
import com.umb.myapplication.features.rfn006.ui.viewmodel.Rfn006ViewModelFactory
import com.umb.myapplication.features.rfn007.ui.Rfn007Activity
import kotlinx.android.synthetic.main.status_bar.view.*
import java.util.*

class Rfn006Activity : GameActivity(), Rfn006Navigator {

    private lateinit var binding: ActivityRfn006Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_rfn006
        )
        val factory = Rfn006ViewModelFactory(application, this)
        val viewmodel = ViewModelProvider(this, factory).get(Rfn006ViewModel::class.java)
        viewmodel.navigator = this
        viewmodel.idUser = intent.getStringExtra(Companion.ID_USER)
        viewmodel.guardianUser = intent.getStringExtra(Companion.GUARDIAN_USER)
        viewmodel.samplecode = intent.getStringExtra(Companion.SAMPLE_CODE)
        viewmodel.score.observe(this, Observer {
            binding.statusBar.tvPoints.text = it.toString()
        })
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        activateViews()
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
        val intent = Intent(this, Rfn007Activity::class.java)
        intent.putExtra(Companion.ID_USER, binding.viewmodel!!.idUser!!)
        intent.putExtra(Companion.GUARDIAN_USER, binding.viewmodel!!.guardianUser!!)
        intent.putExtra(Companion.SAMPLE_CODE, binding.viewmodel!!.samplecode!!)
        startActivity(intent)
    }

    private fun activateViews() {
        val dialog = DialogInstructions(
            this,
            getString(R.string.test_rfn008_instructions),
            R.raw.rfn006_instructivo,
            object : DialogInstructionsListener {
                override fun dismmissDialog() {
                    binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
                    binding.statusBar.tvTime.start()
                    binding.viewmodel?.startTime = Date()
                }
            })
        dialog.show()
    }

    /**Animations**/
    override fun slideView(element: Int, isRigth: Boolean, x: Int, doOnEnd: (x: Int) -> Unit) {
        val otherArrow: View
        val arrow: View
        val text: View
        when (element) {
            1, 4 -> {
                text = binding.textView10
                if (isRigth) {
                    arrow = binding.imageView5
                    otherArrow = binding.imageView4
                } else {
                    arrow = binding.imageView4
                    otherArrow = binding.imageView5
                }
            }
            2, 5 -> {
                text = binding.textView11
                if (isRigth) {
                    arrow = binding.imageView8
                    otherArrow = binding.imageView6
                } else {
                    arrow = binding.imageView6
                    otherArrow = binding.imageView8
                }
            }
            3, 6 -> {
                text = binding.textView12
                if (isRigth) {
                    arrow = binding.imageView9
                    otherArrow = binding.imageView7
                } else {
                    arrow = binding.imageView7
                    otherArrow = binding.imageView9
                }
            }
            else -> {
                arrow = binding.imageView4
                otherArrow = binding.imageView5
                text = binding.textView10
            }
        }
        arrow.isClickable = false
        otherArrow.isClickable = false
        val fadedArrow = ObjectAnimator.ofFloat(arrow, "alpha", 1f, 0f)
        val slideText = if (isRigth)
            ObjectAnimator.ofFloat(text, "x", text.x, text.x + 500)
        else
            ObjectAnimator.ofFloat(text, "x", text.x, text.x - 500)
        val fidedText = ObjectAnimator.ofFloat(text, "alpha", 1f, 0f)
        val showArrow = ObjectAnimator.ofFloat(text, "alpha", 0f, 1f)
        val showText = ObjectAnimator.ofFloat(arrow, "alpha", 0f, 1f)
        val comaBackText = ObjectAnimator.ofFloat(text, "x", text.x, text.x)

        slideText.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                doOnEnd(x)
                arrow.isClickable = true
                otherArrow.isClickable = true
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}

        })

        AnimatorSet().apply {
            play(fadedArrow.apply { duration = 200 }).before(slideText)
            play(fidedText.apply { duration = 500 }).with(slideText.apply { duration = 500 })
            play(showArrow).after(slideText)
            play(showText).with(showArrow.apply { duration = 500 })
            play(comaBackText.apply { duration = 0 }).with(showArrow)
            start()
        }
    }
}