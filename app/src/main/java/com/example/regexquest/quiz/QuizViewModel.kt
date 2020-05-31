package com.example.regexquest.quiz

import android.app.Application
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.regexquest.database.QuizDatabaseDao
import com.example.regexquest.R

class QuizViewModel(
    val database: QuizDatabaseDao,
    application: Application): AndroidViewModel(application){

    private val tag = "QuizViewModel"

    private val quizAmount = 1
    private var currentQuizIndex = 0
    private val gameTimeLimit = 30000L
    private val timeBonusLimit = 10000L
    private val timeBonusAmount = 10
    private val answerPoint = 10

    private val _navigateToResult = MutableLiveData<Boolean?>()
    val navigateToResult: LiveData<Boolean?>
        get() = _navigateToResult

    // minus points depending on answer count
    private val minusPointList = listOf<Int>(0,5,10,15)

    private val quizzes: MutableList<Quiz?> = mutableListOf(
        Quiz(
            quiz = "foobar",
            match = "%foo%bar",
            answers = mutableListOf("f..", "f.*", "f+", "bar")
        ),
        Quiz(
            quiz = "cat\ncan",
            match = "%cat%\n%can%",
            answers = mutableListOf("ca.", "ca+", "ca*", "c*+")
        ),
        Quiz(
            quiz = "foo\nboo",
            match = "%foo%\n%boo%",
            answers = mutableListOf(".oo", ".o", "o.*", "oo.")
        )
    )


    private val _currentQuiz = MutableLiveData<Quiz?>()
    val currentQuiz: LiveData<Quiz?>
        get() = _currentQuiz

    val spannableMatch = Transformations.map(currentQuiz) {quiz ->
        formatMatch(quiz)
    }

    // variables for score
    var correctAnswerCount = 0
    var wrongAnswerCount = 0

    // variables for display
    private val _point = MutableLiveData<Int>()
    val point: LiveData<Int>
        get() = _point
    val pointText = Transformations.map(point) {point ->
        "Point: ${point.toString()}"
    }

    private val _pointAnimationText = MutableLiveData<String>()
    val pointAnimationText: LiveData<String>
        get() = _pointAnimationText

    private val _isPointPlus = MutableLiveData<Boolean>()
    val isPointPlus: LiveData<Boolean>
        get() = _isPointPlus

    private var answerCount = 0

    private val _enableAnswer1 = MutableLiveData<Boolean?>()
    val enableAnswer1: LiveData<Boolean?>
        get() = _enableAnswer1

    private val _enableAnswer2 = MutableLiveData<Boolean?>()
    val enableAnswer2: LiveData<Boolean?>
        get() = _enableAnswer2

    private val _enableAnswer3 = MutableLiveData<Boolean?>()
    val enableAnswer3: LiveData<Boolean?>
        get() = _enableAnswer3

    private val _enableAnswer4 = MutableLiveData<Boolean?>()
    val enableAnswer4: LiveData<Boolean?>
        get() = _enableAnswer4

    private val _animation = MutableLiveData<Boolean?>()
    val animation: LiveData<Boolean?>
        get() = _animation

    // progress of progress bar of game time
    private val _progress = MutableLiveData<Int?>()
    val progress: LiveData<Int?>
        get() = _progress


    // timer of game
    private val timer = object:CountDownTimer(gameTimeLimit, 50) {
        override fun onTick(millisUntilFinished: Long) {
            _progress.value = ( ( (gameTimeLimit - millisUntilFinished ) * 100 / gameTimeLimit ) ).toInt()
        }
        override fun onFinish() {
            onEndOfQuiz()
        }
    }

    private var timeBonus = timeBonusAmount

    // count time bonus
    private val timeBonusTimer = object:CountDownTimer(timeBonusLimit, 10000) {
        override fun onTick(millisUntilFinished: Long) {
            Log.i(tag, "onTick eachQuiz")
            timeBonus = timeBonusAmount
        }
        override fun onFinish() {
            Log.i(tag, "onFinish eachQuiz")
            timeBonus = 0
        }
    }

    init{
        _point.value = 0
        clearParameters()
        setCurrentQuiz()
        _animation.value = false
        timer.start()
    }


    fun onAnswer(index: Int){

        // if answer is correct...
        if(quizzes[currentQuizIndex]!!.answers[0] == _currentQuiz.value!!.answers[index]){

            // calculate point
            _point.value = _point.value?.plus(answerPoint)
            correctAnswerCount ++

            // if it is first answer, add time bonus
            if(answerCount == 0){
                _point.value = _point.value?.plus(timeBonus)
                setPointAnimationText(answerPoint, timeBonus)
            }else{
                setPointAnimationText(answerPoint,0)
            }

            currentQuizIndex ++

            //  after final question
            if(currentQuizIndex >= quizAmount){
                _animation.value = true
                onEndOfQuiz()
            }else{
                clearParameters()
                setCurrentQuiz()
            }

        // if answer is wrong...
        }else{
            when(index){
                0 -> _enableAnswer1.value = false
                1 -> _enableAnswer2.value = false
                2 -> _enableAnswer3.value = false
                3 -> _enableAnswer4.value = false
            }
            answerCount ++
            wrongAnswerCount ++

            _point.value = _point.value?.minus(minusPointList[answerCount])
            setPointAnimationText(-1 * minusPointList[answerCount], 0)
        }
        _animation.value = true

    }

    fun doneNavigate(){
        _navigateToResult.value = false
    }

    fun doneAnimation(){
        _animation.value = false
    }

    private fun clearParameters(){
        answerCount = 0
        _enableAnswer1.value = true
        _enableAnswer2.value = true
        _enableAnswer3.value = true
        _enableAnswer4.value = true
        timeBonusTimer.start()
    }

    private fun setCurrentQuiz(){

        // deep copy of current quiz
        val tmpQuiz = quizzes[currentQuizIndex]
        _currentQuiz.value = Quiz(
            quiz = tmpQuiz!!.quiz,
            match = tmpQuiz!!.match,
            answers = tmpQuiz!!.answers.toMutableList()
        )

        _currentQuiz.value?.answers?.shuffle()
    }

   // edit style of match text
    private fun formatMatch(quiz: Quiz?):SpannableString{
        val match = quiz!!.match
        val matchText = match.replace("%", "")
        var startIndex = 0
        var endIndex = 0
        var lastEndIndex = -1
        var matchCount = 0

        var spannable = SpannableString(matchText)


        while(startIndex != -1){
            startIndex = match.indexOf("%", lastEndIndex + 1)
            endIndex = match.indexOf("%", startIndex+1)
            lastEndIndex = endIndex
            if(startIndex != -1){
                setSpan(spannable, startIndex - 2 * matchCount, endIndex  - 2 * matchCount -1)
                matchCount ++
            }
        }

        spannable.replace("%".toRegex(), "")

        return spannable
    }

    private fun setSpan(spannable: SpannableString, startIndex:Int, endIndex:Int){
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(getApplication() ,R.color.colorPrimaryDark)),
            startIndex, endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }


    private fun setPointAnimationText(point:Int, timeBonus:Int){

        if(point > 0){
            _isPointPlus.value = true
            if(timeBonus > 0){
                _pointAnimationText.value = "Time Bonus +${timeBonus + point}"
            }else{
                _pointAnimationText.value = "+${point}"
            }
        }else{
            _isPointPlus.value = false
            _pointAnimationText.value = "${point}"
        }

    }

    private val endTimer = object:CountDownTimer(1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            _navigateToResult.value = true
        }
    }

    private fun onEndOfQuiz(){
        _enableAnswer1.value = true
        _enableAnswer2.value = true
        _enableAnswer3.value = true
        _enableAnswer4.value = true

        endTimer.start()
    }
}
