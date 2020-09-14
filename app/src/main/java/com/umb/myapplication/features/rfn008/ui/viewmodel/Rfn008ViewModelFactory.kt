package com.umb.myapplication.features.rfn008.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Rfn008ViewModelFactory(
    private val application: Application, private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Rfn008ViewModel::class.java)) {
            return Rfn008ViewModel(application, context) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}