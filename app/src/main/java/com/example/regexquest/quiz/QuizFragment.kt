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


        val viewModelFactory = QuizViewModelFactory()
        val quizViewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(QuizViewModel::class.java)
        binding.quizViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        quizViewModel.navigateToResult.observe(this, Observer {
            if(it == true){
                this.findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToResultFragment()
                )
                quizViewModel.doneNavigate()
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
