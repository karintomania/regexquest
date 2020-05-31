package com.example.regexquest.database


import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.regexquest.quiz.Quiz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [QuizEntity::class, HighScore::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {

    abstract val quizDatabaseDao: QuizDatabaseDao
    abstract val highScoreDao: HighScoreDao


    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getInstance(context: Context): QuizDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = createInstance(context)
                    INSTANCE = instance
                }

                return instance
            }
        }


        private fun createInstance( context: Context ) =Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_database")
//                .createFromAsset("database/quiz_database.db")
                .fallbackToDestructiveMigration()
                .build()

        }


}
