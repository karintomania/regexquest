package com.example.regexquest.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val _navigateToResult = MutableLiveData<Boolean?>()
    val navigateToResult: LiveData<Boolean?>
        get() = _navigateToResult

    fun onAnswer(){
        _navigateToResult.value = true
    }

    fun doneNavigate(){
        _navigateToResult.value = false
    }

}
