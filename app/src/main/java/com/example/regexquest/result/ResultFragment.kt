package com.example.regexquest.result

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.regexquest.R
import com.example.regexquest.database.QuizDatabase
import com.example.regexquest.databinding.ResultFragmentBinding
import com.example.regexquest.quiz.QuizFragmentDirections
import com.example.regexquest.title.TitleViewModel
import com.example.regexquest.title.TitleViewModelFactory

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var viewModel: ResultViewModel
    private lateinit var binding: ResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.result_fragment, container, false)

        val args = ResultFragmentArgs.fromBundle(requireArguments())

        // datasource
        val application = requireNotNull(this.activity).application
        val dataSource = QuizDatabase.getInstance(application).highScoreDao

        val viewModelFactory = ResultViewModelFactory(dataSource, args.correctAnswerCount, args.wrongAnswerCorrect,  args.point, args.difficulty)
        val resultViewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ResultViewModel::class.java)
        binding.resultViewModel = resultViewModel
        binding.lifecycleOwner = viewLifecycleOwner


        resultViewModel.navigateToTitle.observe(this, Observer {
            if(it == true){
                this.findNavController().navigate(
                    ResultFragmentDirections.actionResultFragmentToTitleFragment()
                )
                resultViewModel.doneNavigate()
            }
        })

        resultViewModel.isHighScore.observe(this, Observer {
            if(it == true){
                binding.textNewHighScore.visibility = View.VISIBLE
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
    }

}
