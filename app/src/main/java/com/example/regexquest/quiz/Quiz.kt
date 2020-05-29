package com.example.regexquest.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Quiz (
    val quiz: String,
    val match: String,
    val answers: MutableList<String>
)
