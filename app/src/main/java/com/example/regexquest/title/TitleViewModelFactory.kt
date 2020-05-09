package com.example.regexquest.title

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TitleViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TitleViewModel::class.java)) {
            return TitleViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

