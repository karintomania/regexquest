package com.example.regexquest.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel(
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

    init{
        _totalPoint.value = point.toString()
        _rank.value = "A"
    }

    fun onBackToTitle(){
        _navigateToTitle.value = true
    }

    fun doneNavigate(){
        _navigateToTitle.value = false
    }
}
