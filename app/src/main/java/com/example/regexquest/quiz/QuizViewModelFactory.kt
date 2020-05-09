package com.example.regexquest.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.regexquest.result.ResultViewModel

class QuizViewModelFactory : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
                return QuizViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
