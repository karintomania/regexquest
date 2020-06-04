package com.example.regexquest.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Quiz (
    val quizId: String,
    val quizText: String,
    val answers: MutableList<String>
)
