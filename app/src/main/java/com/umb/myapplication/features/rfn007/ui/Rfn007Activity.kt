package com.umb.myapplication.features.rfn007.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.R
import com.umb.myapplication.core.ui.GameActivity
import com.umb.myapplication.core.utils.DialogInstructions
import com.umb.myapplication.core.utils.DialogInstructionsListener
import com.umb.myapplication.databinding.ActivityRfn007Binding
import com.umb.myapplication.features.rfn007.ui.viewmodel.Rfn007ViewModel
import com.umb.myapplication.features.rfn007.ui.viewmodel.Rfn007ViewModelFactory
import com.umb.myapplication.features.rfn008.ui.Rfn008Activity
import kotlinx.android.synthetic.main.status_bar.view.*
import java.util.*

class Rfn007Activity : GameActivity() ,
    Rfn007Navigator {

    private lateinit var binding : ActivityRfn007Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rfn007)
        val factory = Rfn007ViewModelFactory(application, this)
        val viewmodel = ViewModelProvider(this, factory).get(Rfn007ViewModel::class.java)
        viewmodel.navigator = this
        viewmodel.idUser = intent.getStringExtra(Companion.ID_USER)
        viewmodel.guardianUser = intent.getStringExtra(Companion.GUARDIAN_USER)
        viewmodel.sampleCode = intent.getStringExtra(Companion.SAMPLE_CODE)
        viewmodel.score.observe(this, Observer {
            binding.statusBar.tvPoints.text = it.toString()
        })
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        activateViews()
        startSpeechToText(this,binding.microphone)
    }

    private fun activateViews() {
        val dialog = DialogInstructions(
            this,
            getString(R.string.test_rfn008_instructions),
            R.raw.rfn008_instrucciones,
            object : DialogInstructionsListener {
                override fun dismmissDialog() {
                    binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
                    binding.statusBar.tvTime.start()

                    //Metodos que se usaran

                    binding.viewmodel!!.playInitSound()
                }
            })
        dialog.show()
    }

    override fun getUserID(): String {
        TODO("Not yet implemented")
    }

    override fun toNextActvity() {
        val intent = Intent(this, Rfn008Activity::class.java)
        intent.putExtra(Companion.ID_USER, binding.viewmodel!!.idUser!!)
        intent.putExtra(Companion.GUARDIAN_USER, binding.viewmodel!!.guardianUser!!)
        intent.putExtra(Companion.SAMPLE_CODE, binding.viewmodel!!.sampleCode!!)
        startActivity(intent)
    }

    override fun activateTest() {
        binding.statusBar.tvTime.base = SystemClock.elapsedRealtime()
        binding.statusBar.tvTime.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun startSpeechToText(context: Context, btSpeech: ImageView) {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,  Locale.getDefault())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {
                Log.d("SpeechToText", "onReadyForSpeech")

            }

            override fun onBeginningOfSpeech() {
                Log.d("SpeechToText", "onBeginningOfSpeech")
            }

            override fun onRmsChanged(v: Float) {
                Log.d("SpeechToText", "onRmsChanged")
            }

            override fun onBufferReceived(bytes: ByteArray) {
                Log.d("SpeechToText", "onBufferReceived")
            }

            override fun onEndOfSpeech() {
                Log.d("SpeechToText", "onEndOfSpeech")

            }

            override fun onError(i: Int) {
                Log.d("SpeechToText", "onError")
            }

            override fun onResults(bundle: Bundle) {
                Log.d("SpeechToText", "onResults")
                val matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)//getting all the matches
                //displaying the first match
                if (matches != null) {
                    binding.viewmodel!!.evaluateText(matches[0])
//                    val builder = StringBuilder()
//                    if (!editText.text.isNullOrEmpty()) {
//                        builder.append(editText.text)
//                        builder.append(" ")
//                    }
//                    builder.append(matches[0])
//                    editText.setText(builder.toString())
                }
            }

            override fun onPartialResults(bundle: Bundle) {
                Log.d("SpeechToText", "onPartialResults")
            }

            override fun onEvent(i: Int, bundle: Bundle) {
                Log.d("SpeechToText", "onEvent")
            }
        })

        btSpeech.setOnTouchListener { _, motionEvent ->
            Log.d("SpeechToText", "Action: ${motionEvent.action}")
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    Log.d("SpeechToText", "ACTION_UP")
                    speechRecognizer.stopListening()
                    binding.viewmodel!!.playNext()
                    return@setOnTouchListener true
                }

                MotionEvent.ACTION_DOWN -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        validatePermission()
                    }
                    Log.d("SpeechToText", "ACTION_DOWN")
                    speechRecognizer.startListening(speechRecognizerIntent)
                     return@setOnTouchListener true
                }
            }
            false
        }
    }


}