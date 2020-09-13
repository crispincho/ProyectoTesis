package com.umb.myapplication.features.rfn006.ui

import android.view.View

interface Rfn006Navigator {

    fun toNextActvity()

    fun activateViews()

    fun goToNextTest()

    fun slideView(arrow: View, text: View, isRigth: Boolean, x: Int, doOnEnd: (x: Int) -> Unit)

}