package com.example.regexquest.database

import androidx.room.*

@Dao
interface HighScoreDao {

    // High score
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(highScore: HighScore)

    @Query("SELECT * from high_score WHERE difficulty = :difficulty")
    fun get(difficulty: Int): HighScore?

    @Update
    fun update(highScore: HighScore)
}
