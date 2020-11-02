package com.umb.myapplication.features.rfn007.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umb.myapplication.features.rfn007.ui.viewmodel.Rfn007ViewModel

class Rfn007ViewModelFactory(private val application: Application, private val context: Context)
    : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Rfn007ViewModel::class.java)) {
            return Rfn007ViewModel(application, context) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}