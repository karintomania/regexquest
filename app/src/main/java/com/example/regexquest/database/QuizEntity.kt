package com.example.regexquest.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class QuizEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    val level: Int,

    val quiz: String,
    val match: String,

    val answer: String,
    val answer2: String,
    val answer3: String,
    val answer4: String
)
