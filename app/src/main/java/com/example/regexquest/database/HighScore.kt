package com.example.regexquest.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_score")
data class HighScore (
        @PrimaryKey
        val difficulty: Int,
        val score: Int
)
