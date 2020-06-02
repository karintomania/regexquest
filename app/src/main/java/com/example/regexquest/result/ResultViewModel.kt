package com.example.regexquest.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.regexquest.database.HighScore
import com.example.regexquest.database.HighScoreDao
import com.example.regexquest.database.QuizDatabaseDao
import kotlinx.coroutines.*

class ResultViewModel(
    val database: HighScoreDao,
    private val correctAnswerCount:Int,
    private val wrongAnswerCount:Int,
    private val point:Int,
    private val difficulty:Int
) : ViewModel() {

    private var tag = "ResultViewModel"
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToTitle = MutableLiveData<Boolean?>()
    val navigateToTitle: LiveData<Boolean?>
        get() = _navigateToTitle

    private val _totalPoint = MutableLiveData<String?>()
    val totalPoint: LiveData<String?>
        get() = _totalPoint

    private val _rank = MutableLiveData<String?>()
    val rank: LiveData<String?>
        get() = _rank

    private val _correctAnswerCountText = MutableLiveData<String?>()
    val correctAnswerCountText: LiveData<String?>
        get() = _correctAnswerCountText

    private val _wrongAnswerCountText = MutableLiveData<String?>()
    val wrongAnswerCountText: LiveData<String?>
        get() = _wrongAnswerCountText

    private val _accuracyText = MutableLiveData<String?>()
    val accuracyText: LiveData<String?>
        get() = _accuracyText

    private val _classText = MutableLiveData<String?>()
    val classText: LiveData<String?>
        get() = _classText

    private val _highScoreText = MutableLiveData<String?>()
    val highScoreText: LiveData<String?>
        get() = _highScoreText

    private val _isHighScore = MutableLiveData<Boolean>()
    val isHighScore: LiveData<Boolean?>
        get() = _isHighScore

    init{
        _totalPoint.value = point.toString()
        _rank.value = getRank()
        _classText.value = getClass()
        _correctAnswerCountText.value = correctAnswerCount.toString()
        _wrongAnswerCountText.value   = wrongAnswerCount.toString()


       setHighScore()

        val accuracy = calcAccuracy(correctAnswerCount, wrongAnswerCount)
        _accuracyText.value = "%.2f".format(accuracy) + "%"


    }

    fun onBackToTitle(){
        _navigateToTitle.value = true
    }

    fun doneNavigate(){
        _navigateToTitle.value = false
    }

    private fun getRank():String{
       return when{
           point > 120 -> "SSS"
           point > 110 -> "SS"
           point > 100 -> "S"
           point > 90  -> "A"
           point > 70  -> "B"
           point > 50  -> "C"
           point > 30  -> "D"
           else        -> "E"
       }
    }

    private fun getClass():String{
        return when(difficulty){
            0 -> "Junior"
            1 -> "Senior"
            2 -> "CTO"
            else -> ""
        }
    }

    private fun calcAccuracy(correctAnswerCount: Int, wrongAnswerCount: Int):Float{
        var accuracy = 0f
        if(correctAnswerCount != 0 || wrongAnswerCount != 0){
            accuracy = correctAnswerCount.div((correctAnswerCount + wrongAnswerCount).toFloat()) * 100f
        }
        return accuracy
    }

    private fun setHighScore(){
        Log.i(tag, "setHighScore")
        uiScope.launch {
            val highScore = getHighScore(difficulty)
            val existsHighScore = (highScore != null)
            val highScorePoint = highScore?.score?:0

            if(point > highScorePoint){
                if(existsHighScore){
                    updatetHighScore(HighScore(difficulty,point))
                }else{
                    insertHighScore(HighScore(difficulty,point))
                }
                _isHighScore.value = true
                _highScoreText.value = point.toString()
            }else{
                _isHighScore.value = false
                _highScoreText.value = highScorePoint.toString()
            }

        }

//        withContext(Dispatchers.IO) {
//            val highScore = database.get(difficulty)
//            val highScorePoint = highScore?.score?:0
//
//            if(point > highScorePoint){
//                database.insert(HighScore(difficulty, point))
//                _isHighScore.value = true
//            }
//
//            _highScoreText.value = highScorePoint.toString()
//        }
    }

    private suspend fun getHighScore(difficulty: Int) : HighScore? {
        return withContext(Dispatchers.IO) {
            database.get(difficulty)
        }
    }

    private suspend fun updatetHighScore(highScore: HighScore) {
        withContext(Dispatchers.IO) {
            database.update(highScore)
        }
    }
    private suspend fun insertHighScore(highScore: HighScore) {
        withContext(Dispatchers.IO) {
            database.insert(highScore)
        }
    }

}
