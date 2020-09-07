package com.umb.myapplication.features.rfn006.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Rfn006ViewModelFactory(
    private val application: Application, private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Rfn006ViewModel::class.java)) {
            return Rfn006ViewModel(application, context) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}