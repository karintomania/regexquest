package com.example.regexquest.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuizDatabaseDao {

    @Insert
    fun insert(quiz: QuizEntity)

    @Insert
    fun insert(quizzes: List<QuizEntity>)

    @Update
    fun update(quiz: QuizEntity)

    @Query("SELECT * from quiz WHERE id = :key")
    fun get(key: Long): QuizEntity?

//    @Query("DELETE FROM daily_sleep_quality_table")
//    fun clear()
//
//    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
//    fun getTonight(): QuizNight?

    @Query("SELECT * FROM quiz WHERE level = :level")
    fun selectByLevel(level: Int): List<QuizEntity>


}
