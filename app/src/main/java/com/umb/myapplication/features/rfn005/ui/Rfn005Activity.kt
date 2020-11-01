package com.umb.myapplication.features.rfn005.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
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

class Rfn005Activity : GameActivity(), Rfn005Navigator {

    lateinit var binding: ActivityRfn005Binding
    private lateinit var timer: Timer

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
    }

    private fun assignObserversButtons() {
        binding.viewmodel?.buttonsMutableList!![0].observe(
            this,
            Observer { rotateButton(binding.imageButton10, it[0]) })
        binding.viewmodel?.buttonsMutableList!![1].observe(
            this,
            Observer { rotateButton(binding.imageButton11, it[0]) })
        binding.viewmodel?.buttonsMutableList!![2].observe(
            this,
            Observer { rotateButton(binding.imageButton12, it[0]) })
        binding.viewmodel?.buttonsMutableList!![3].observe(
            this,
            Observer { rotateButton(binding.imageButton13, it[0]) })
        binding.viewmodel?.buttonsMutableList!![4].observe(
            this,
            Observer { rotateButton(binding.imageButton14, it[0]) })
        binding.viewmodel?.buttonsMutableList!![5].observe(
            this,
            Observer { rotateButton(binding.imageButton15, it[0]) })
        binding.viewmodel?.buttonsMutableList!![6].observe(
            this,
            Observer { rotateButton(binding.imageButton16, it[0]) })
        binding.viewmodel?.buttonsMutableList!![7].observe(
            this,
            Observer { rotateButton(binding.imageButton17, it[0]) })
        binding.viewmodel?.buttonsMutableList!![8].observe(
            this,
            Observer { rotateButton(binding.imageButton18, it[0]) })
        binding.viewmodel?.buttonsMutableList!![9].observe(
            this,
            Observer { rotateButton(binding.imageButton20, it[0]) })
        binding.viewmodel?.buttonsMutableList!![10].observe(
            this,
            Observer { rotateButton(binding.imageButton21, it[0]) })
        binding.viewmodel?.buttonsMutableList!![11].observe(
            this,
            Observer { rotateButton(binding.imageButton22, it[0]) })
        binding.viewmodel?.buttonsMutableList!![12].observe(
            this,
            Observer { rotateButton(binding.imageButton23, it[0]) })
        binding.viewmodel?.buttonsMutableList!![13].observe(
            this,
            Observer { rotateButton(binding.imageButton24, it[0]) })
        binding.viewmodel?.buttonsMutableList!![14].observe(
            this,
            Observer { rotateButton(binding.imageButton25, it[0]) })
        binding.viewmodel?.buttonsMutableList!![15].observe(
            this,
            Observer { rotateButton(binding.imageButton26, it[0]) })
        binding.viewmodel?.buttonsMutableList!![16].observe(
            this,
            Observer { rotateButton(binding.imageButton27, it[0]) })
        binding.viewmodel?.buttonsMutableList!![17].observe(
            this,
            Observer { rotateButton(binding.imageButton28, it[0]) })
        binding.viewmodel?.buttonsMutableList!![18].observe(
            this,
            Observer { rotateButton(binding.imageButton01, it[0]) })
        binding.viewmodel?.buttonsMutableList!![19].observe(
            this,
            Observer { rotateButton(binding.imageButton02, it[0]) })
    }

    private fun assignClickListenerButtons() {
        binding.imageButton10.setOnClickListener { clicEvent(it, binding.viewmodel, 0) }
        binding.imageButton11.setOnClickListener { clicEvent(it, binding.viewmodel, 1) }
        binding.imageButton12.setOnClickListener { clicEvent(it, binding.viewmodel, 2) }
        binding.imageButton13.setOnClickListener { clicEvent(it, binding.viewmodel, 3) }
        binding.imageButton14.setOnClickListener { clicEvent(it, binding.viewmodel, 4) }
        binding.imageButton15.setOnClickListener { clicEvent(it, binding.viewmodel, 5) }
        binding.imageButton16.setOnClickListener { clicEvent(it, binding.viewmodel, 6) }
        binding.imageButton17.setOnClickListener { clicEvent(it, binding.viewmodel, 7) }
        binding.imageButton18.setOnClickListener { clicEvent(it, binding.viewmodel, 8) }
        binding.imageButton20.setOnClickListener { clicEvent(it, binding.viewmodel, 9) }
        binding.imageButton21.setOnClickListener { clicEvent(it, binding.viewmodel, 10) }
        binding.imageButton22.setOnClickListener { clicEvent(it, binding.viewmodel, 11) }
        binding.imageButton23.setOnClickListener { clicEvent(it, binding.viewmodel, 12) }
        binding.imageButton24.setOnClickListener { clicEvent(it, binding.viewmodel, 13) }
        binding.imageButton25.setOnClickListener { clicEvent(it, binding.viewmodel, 14) }
        binding.imageButton26.setOnClickListener { clicEvent(it, binding.viewmodel, 15) }
        binding.imageButton27.setOnClickListener { clicEvent(it, binding.viewmodel, 16) }
        binding.imageButton28.setOnClickListener { clicEvent(it, binding.viewmodel, 17) }
    }

    private fun clicEvent(button: View, viewModel: Rfn005ViewModel?, position: Int) {
        if (button.isSelected) {
            viewModel?.buttonsMutableList!![position].value?.set(1, 0)
            button.isSelected = false
        } else {
            viewModel?.buttonsMutableList!![position].value?.set(1, 1)
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
    private fun rotateButton(button: ImageButton, direction: Int) {
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
    }

    private fun activateViews() {
        val dialog = DialogInstructions(
            this,
            getString(R.string.test_rfn005_instructions),
            R.raw.rfn005_instrucciones,
            object : DialogInstructionsListener {
                override fun dismmissDialog() {
                    assignObserversButtons()
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