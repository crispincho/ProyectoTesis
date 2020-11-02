package com.umb.myapplication.features.rfn009.ui


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
import com.umb.myapplication.databinding.ActivityRfn009Binding
import com.umb.myapplication.features.rfn009.ui.viewmodel.Rfn009ViewModel
import com.umb.myapplication.features.rfn009.ui.viewmodel.Rfn009ViewModelFactory
import kotlinx.android.synthetic.main.status_bar.view.*

class Rfn009Activity : GameActivity(), Rfn009Navigator {

    lateinit var binding: ActivityRfn009Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rfn009)
        val factory = Rfn009ViewModelFactory(application, this)
        val viewmodel = ViewModelProvider(this, factory).get(Rfn009ViewModel::class.java)
        viewmodel.navigator = this
        viewmodel.idUser = intent.getStringExtra(Companion.ID_USER) ?: ""
        viewmodel.score.observe(this, Observer {
            binding.statusBar.tvPoints.text = it.toString()
        })
        binding.viewmodel = viewmodel
        binding.workCorrect.visibility = View.INVISIBLE
        binding.lifecycleOwner = this
        activateView()
    }

    private fun activateView() {
        val dialog = DialogInstructions(
            this,
            getString(R.string.test_rfn009_instructions),
            R.raw.rfn008_instrucciones,
            object : DialogInstructionsListener {
                override fun dismmissDialog() {
                    binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
                    binding.statusBar.tvTime.start()

                    binding.viewmodel!!.initWork(binding.work)
                }
            })
        dialog.show()
    }

    override fun toNextActvity() {
        finish()
        System.exit(0)
    }

    override fun activateTest() {
        binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
        binding.statusBar.tvTime.start()
    }

    override fun getUserID(): String {
        return intent.getStringExtra(ID_USER)!!
    }


}