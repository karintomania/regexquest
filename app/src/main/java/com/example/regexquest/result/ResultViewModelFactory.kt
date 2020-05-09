package com.example.regexquest.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.regexquest.title.TitleViewModel

class ResultViewModelFactory: ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
                return ResultViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}
