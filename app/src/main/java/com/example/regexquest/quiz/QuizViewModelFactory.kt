package com.example.regexquest.quiz

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.regexquest.database.QuizDatabaseDao
import com.example.regexquest.result.ResultViewModel

class QuizViewModelFactory(
    private val dataSource: QuizDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
                return QuizViewModel(dataSource,application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
