package com.umb.myapplication.features.rfn005.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.umb.myapplication.R
import com.umb.myapplication.core.ui.GameActivity
import com.umb.myapplication.core.utils.DialogInstructions
import com.umb.myapplication.core.utils.DialogInstructionsListener
import com.umb.myapplication.databinding.ActivityRfn005Binding
import com.umb.myapplication.features.rfn005.ui.viewmodel.Rfn005ViewModel
import com.umb.myapplication.features.rfn005.ui.viewmodel.Rfn005ViewModelFactory
import com.umb.myapplication.features.rfn006.ui.Rfn006Activity
import kotlinx.android.synthetic.main.status_bar.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class Rfn005Activity : GameActivity(), Rfn005Navigator{

    lateinit var binding: ActivityRfn005Binding
    private lateinit var timer: Timer
    private lateinit var buttonsList: MutableList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_rfn005
        )
        val factory = Rfn005ViewModelFactory(application, this)
        val viewModel = factory.create(Rfn005ViewModel::class.java)

        viewModel.points.observe(this, Observer {
            binding.statusBar.tvPoints.text = it.toString()
        })
        viewModel.navigator = this
        viewModel.idUser = intent.getStringExtra(Companion.ID_USER)
        viewModel.guardianUser = intent.getStringExtra(Companion.GUARDIAN_USER)
        viewModel.sampleCode = intent.getStringExtra(Companion.SAMPLE_CODE)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.statusBar.tvTestDescription.isSelected = true
        activateViews()
        buttonsList = mutableListOf(
            binding.imageButton10,
            binding.imageButton11,
            binding.imageButton12,
            binding.imageButton13,
            binding.imageButton14,
            binding.imageButton15,
            binding.imageButton16,
            binding.imageButton17,
            binding.imageButton18,
            binding.imageButton20,
            binding.imageButton21,
            binding.imageButton22,
            binding.imageButton23,
            binding.imageButton24,
            binding.imageButton25,
            binding.imageButton26,
            binding.imageButton27,
            binding.imageButton28,
            binding.imageButton01,
            binding.imageButton02
        )
    }

    private fun assignClickListenerButtons() {
        binding.imageButton10.setOnClickListener { clicEvent(it, 0) }
        binding.imageButton11.setOnClickListener { clicEvent(it, 1) }
        binding.imageButton12.setOnClickListener { clicEvent(it, 2) }
        binding.imageButton13.setOnClickListener { clicEvent(it, 3) }
        binding.imageButton14.setOnClickListener { clicEvent(it, 4) }
        binding.imageButton15.setOnClickListener { clicEvent(it, 5) }
        binding.imageButton16.setOnClickListener { clicEvent(it, 6) }
        binding.imageButton17.setOnClickListener { clicEvent(it, 7) }
        binding.imageButton18.setOnClickListener { clicEvent(it, 8) }
        binding.imageButton20.setOnClickListener { clicEvent(it, 9) }
        binding.imageButton21.setOnClickListener { clicEvent(it, 10) }
        binding.imageButton22.setOnClickListener { clicEvent(it, 11) }
        binding.imageButton23.setOnClickListener { clicEvent(it, 12) }
        binding.imageButton24.setOnClickListener { clicEvent(it, 13) }
        binding.imageButton25.setOnClickListener { clicEvent(it, 14) }
        binding.imageButton26.setOnClickListener { clicEvent(it, 15) }
        binding.imageButton27.setOnClickListener { clicEvent(it, 16) }
        binding.imageButton28.setOnClickListener { clicEvent(it, 17) }
    }

    private fun clicEvent(button: View, position: Int) {
        if (button.isSelected) {
            binding.viewmodel!!.buttonsMutableList[position][1] = 0
            button.isSelected = false
        } else {
            binding.viewmodel!!.buttonsMutableList[position][1] = 1
            button.isSelected = true
        }
        selectedButton(button)
    }

    private fun activeTimer() {
        timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.viewmodel?.checkChosenOptions()
                }
            }
        }
        timer.schedule(timerTask, 0, binding.viewmodel!!.ROUNDS_TIME)
    }

    /**Animations**/
    override fun rotateButton(buttonPosition: Int, direction: Int) {
        val button = buttonsList[buttonPosition]
        button.isSelected = false
        var animator = ObjectAnimator.ofFloat(button, "rotation", 0f)
        when (direction) {
            0 -> {
                animator = ObjectAnimator.ofFloat(button, "rotation", 270f)
            }
            1 -> {
                animator = ObjectAnimator.ofFloat(button, "rotation", 0f)
            }
            2 -> {
                animator = ObjectAnimator.ofFloat(button, "rotation", 90f)
            }
            3 -> {
                animator = ObjectAnimator.ofFloat(button, "rotation", 180f)
            }
        }
        animator.apply {
            duration = 1000
            start()
        }
    }

    private fun selectedButton(view: View) {
        val presedJump: Animation = AnimationUtils.loadAnimation(this, R.anim.button_selected)
        view.startAnimation(presedJump)
    }

    /**Navigator functions**/
    override fun toNextActvity() {
        timer.cancel()
        timer.purge()
        val intent = Intent(this, Rfn006Activity::class.java)
        intent.putExtra(Companion.ID_USER, binding.viewmodel!!.idUser!!)
        intent.putExtra(Companion.GUARDIAN_USER, binding.viewmodel!!.guardianUser!!)
        intent.putExtra(Companion.SAMPLE_CODE, binding.viewmodel!!.sampleCode!!)
        startActivity(intent)
        finish()
    }

    private fun activateViews() {
        val dialog = DialogInstructions(
            this,
            getString(R.string.test_rfn005_instructions),
            R.raw.rfn005_instructivo,
            object : DialogInstructionsListener {
                override fun dismmissDialog() {
//                    assignObserversButtons()
                    assignClickListenerButtons()
                    activeTimer()
                    binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
                    binding.statusBar.tvTime.start()
                    binding.viewmodel?.startDate = Date()
                }
            })
        dialog.show()
    }
}