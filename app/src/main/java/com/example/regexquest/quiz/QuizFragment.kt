package com.example.regexquest.quiz

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.regexquest.R
import com.example.regexquest.database.QuizDatabase
import com.example.regexquest.databinding.QuizFragmentBinding
import com.example.regexquest.result.ResultFragmentArgs

class QuizFragment : Fragment() {

    companion object {
        fun newInstance() = QuizFragment()
    }

    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: QuizFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.quiz_fragment, container, false)

        val args = QuizFragmentArgs.fromBundle(requireArguments())

        // datasource
        val application = requireNotNull(this.activity).application
        val dataSource = QuizDatabase.getInstance(application).quizDatabaseDao
        val viewModelFactory = QuizViewModelFactory(dataSource,application)

        val quizViewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(QuizViewModel::class.java)
        binding.quizViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        quizViewModel.navigateToResult.observe(this, Observer {
            if(it == true){
                this.findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToResultFragment(
                        quizViewModel.correctAnswerCount,
                        quizViewModel.wrongAnswerCount,
                        quizViewModel.point?.value?:0,
                        args.difficulty)
                )
                quizViewModel.doneNavigate()
            }
        })

        // point animation
        quizViewModel.animation.observe(this, Observer {
            if (it == true) {
                binding.textPointAnimation.apply{
                    alpha = 1f
                    animate().alpha(0f).setDuration(1000L).setListener(null)
                }
                quizViewModel.doneAnimation()
            }
        })

        // change point animation text color
        quizViewModel.isPointPlus.observe(this, Observer {
            if (it == true) {
                binding.textPointAnimation.apply{
                    setTextColor(resources.getColor(R.color.colorPrimaryDark))
                }
            }else{
                binding.textPointAnimation.apply{
                    setTextColor(resources.getColor(R.color.colorMinusPoint))
                }
            }
        })


        setImage(binding,args.difficulty)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private fun setImage(binding: QuizFragmentBinding, difficulty:Int){
        when(difficulty){
            0 -> binding.imageQuiz.setImageResource(R.drawable.quiz_junior)
            1 -> binding.imageQuiz.setImageResource(R.drawable.quiz_senior)
            2 -> binding.imageQuiz.setImageResource(R.drawable.quiz_cto)
        }
    }
}
