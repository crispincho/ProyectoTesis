package com.umb.myapplication.core.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.umb.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogInstructions(
    context: Context,
    private val instruction: String,
    private val instructionAudio: Int,
    private val listener: DialogInstructionsListener
) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.argb(204, 102, 102, 102)))
        setContentView(R.layout.dialog_instructions)

        val tvInstructions = findViewById<TextView>(R.id.instructions)
        tvInstructions.text = instruction

        val lp = WindowManager.LayoutParams()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = lp

        @Suppress("DEPRECATION")
        window?.decorView?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
        dismissDialog()
    }

    private fun dismissDialog() {
        val mediaPlayer = MediaPlayer.create(context, instructionAudio)
        mediaPlayer.start()
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Thread.sleep(mediaPlayer.duration.toLong())
            }
            listener.dismmissDialog()
            this@DialogInstructions.dismiss()
            mediaPlayer.release()
        }
    }

}