package com.example.regexquest.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {
    private val _navigateToTitle = MutableLiveData<Boolean?>()
    val navigateToTitle: LiveData<Boolean?>
        get() = _navigateToTitle


    fun onBackToTitle(){
        _navigateToTitle.value = true
    }

    fun doneNavigate(){
        _navigateToTitle.value = false
    }
}
