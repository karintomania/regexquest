package com.example.regexquest.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.regexquest.quiz.Quiz

@Entity(tableName = "quiz")
data class QuizEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    val difficulty: Int,

    val quiz: String,
    val match: String,

    val answer: String,
    val answer2: String,
    val answer3: String,
    val answer4: String

){
    fun toQuiz(): Quiz {
        return Quiz(
            unescapeNewLine(this.quiz),
            unescapeNewLine(this.match),
            mutableListOf<String>(this.answer, this.answer2, this.answer3, this.answer4))
    }

    private fun unescapeNewLine(description: String): String {
        return description.replace("\\n", System.lineSeparator())
    }
}
