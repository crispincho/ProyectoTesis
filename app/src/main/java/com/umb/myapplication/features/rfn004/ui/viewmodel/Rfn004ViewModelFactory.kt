package com.umb.myapplication.features.rfn004.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class Rfn004ViewmodelFactory(
    private val application: Application, private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Rfn004ViewModel::class.java)) {
            return Rfn004ViewModel(application, context) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}