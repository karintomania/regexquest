package com.example.regexquest.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel(
    private val correctAnswerCount:Int,
    private val wrongAnswerCount:Int,
    private val point:Int,
    private val difficulty:Int
) : ViewModel() {

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

    init{
        _totalPoint.value = point.toString()
        _rank.value = setRank()
        _correctAnswerCountText.value = correctAnswerCount.toString()
        _wrongAnswerCountText.value   = wrongAnswerCount.toString()

        val accuracy = correctAnswerCount.div((correctAnswerCount + wrongAnswerCount).toFloat()) * 100f
        _accuracyText.value = "%.2f".format(accuracy)
    }

    fun onBackToTitle(){
        _navigateToTitle.value = true
    }

    fun doneNavigate(){
        _navigateToTitle.value = false
    }

    private fun setRank():String{
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
}
