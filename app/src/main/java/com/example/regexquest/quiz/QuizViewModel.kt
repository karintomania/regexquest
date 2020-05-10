package com.example.regexquest.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val tag = "QuizViewModel"

    private val quizAmount = 3;
    private var currentQuizIndex = 0


    private val _navigateToResult = MutableLiveData<Boolean?>()
    val navigateToResult: LiveData<Boolean?>
        get() = _navigateToResult

    private val quizzes: MutableList<Quiz?> = mutableListOf(
        Quiz(quiz = "foo",match = "foo",answers = mutableListOf("answer","b","c","d")),
        Quiz(quiz = "bar",match = "bar",answers = mutableListOf("answer","b","c","d")),
        Quiz(quiz = "foobar",match = "foobar",answers = mutableListOf("answer","b","c","d"))
    )


    private val _currentQuiz = MutableLiveData<Quiz?>()
    val currentQuiz: LiveData<Quiz?>
        get() = _currentQuiz

    init{
        setCurrentQuiz()
    }

    fun onAnswer(index: Int){
        if(quizzes[currentQuizIndex]!!.answers[0] == _currentQuiz.value!!.answers[index]){
            currentQuizIndex ++

            if(currentQuizIndex >= quizAmount){
                _navigateToResult.value = true
            }else{
                setCurrentQuiz()
            }
        }
    }

    fun doneNavigate(){
        _navigateToResult.value = false
    }

    fun setCurrentQuiz(){
        // deep copy of current quiz
        val tmpQuiz = quizzes[currentQuizIndex]
        _currentQuiz.value = Quiz(quiz = tmpQuiz!!.quiz, match = tmpQuiz!!.match, answers = tmpQuiz!!.answers.toMutableList())

        _currentQuiz.value?.answers?.shuffle()
    }

}
