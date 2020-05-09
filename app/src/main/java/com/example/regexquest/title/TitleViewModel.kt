package com.example.regexquest.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleViewModel : ViewModel() {
    private val _navigateToQuiz = MutableLiveData<Boolean?>()
    val navigateToQuiz: LiveData<Boolean?>
        get() = _navigateToQuiz

    fun onStart(){
        _navigateToQuiz.value = true
    }

    fun doneNavigate(){
        _navigateToQuiz.value = false
    }

}
