package com.example.regexquest.title

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleViewModel : ViewModel() {
    private val _navigateToQuiz = MutableLiveData<Boolean?>()
    val navigateToQuiz: LiveData<Boolean?>
        get() = _navigateToQuiz

    private val tag = "TitleFragment"
    var difficulty = 0

    fun onStart(difficulty:Int){
        Log.i(tag, "onStart")
        this.difficulty = difficulty
        _navigateToQuiz.value = true
    }

    fun doneNavigate(){
        _navigateToQuiz.value = false
    }

}
