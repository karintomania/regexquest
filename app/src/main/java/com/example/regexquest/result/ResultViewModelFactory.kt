package com.example.regexquest.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.regexquest.database.HighScoreDao
import com.example.regexquest.database.QuizDatabaseDao

class ResultViewModelFactory(
    val database: HighScoreDao,
    private val correctAnswerCount:Int,
    private val wrongAnswerCount:Int,
    private val point:Int,
    private val difficulty:Int
    ): ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
                return ResultViewModel(database, correctAnswerCount, wrongAnswerCount, point, difficulty) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}
