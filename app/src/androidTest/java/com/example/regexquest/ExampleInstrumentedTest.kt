package com.example.regexquest

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.regexquest.database.QuizDatabase
import com.example.regexquest.database.QuizDatabaseDao
import com.example.regexquest.database.QuizEntity
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.regexquest", appContext.packageName)
//    }

    private lateinit var quizDao: QuizDatabaseDao
    private lateinit var db: QuizDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, QuizDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        quizDao = db.quizDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetQuiz() {
//        val quizzes = quizDao.get(1L)
//        assertEquals(quizzes?.id, 1L)
//    }


//    @Test
//    @Throws(Exception::class)
//    fun selectByLevel() {
//
//        val quizzes = quizDao.selectByLevel(1)
//        assertEquals(quizzes.size, 1)
//        assertEquals(quizzes.get(0)?.level?:0 , 1)
//    }

}
