package com.example.regexquest.quiz

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.animate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.regexquest.R
import com.example.regexquest.database.QuizDatabase
import com.example.regexquest.databinding.QuizFragmentBinding

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
                    QuizFragmentDirections.actionQuizFragmentToResultFragment(quizViewModel.point.value!!.toInt(), 0)
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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

}
