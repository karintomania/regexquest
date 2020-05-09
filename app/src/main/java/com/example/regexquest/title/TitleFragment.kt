package com.example.regexquest.title

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.regexquest.R
import com.example.regexquest.databinding.TitleFragmentBinding

class TitleFragment : Fragment() {

    companion object {
        fun newInstance() = TitleFragment()
    }

    private lateinit var binding: TitleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)

        val viewModelFactory = TitleViewModelFactory()
        val titleViewModel = ViewModelProviders
                        .of(this, viewModelFactory)
                        .get(TitleViewModel::class.java)
        binding.titleViewModel = titleViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        titleViewModel.navigateToQuiz.observe(this, Observer {
            if(it == true){
                this.findNavController().navigate(
                    TitleFragmentDirections.actionTitleFragmentToQuizFragment()
                )

                titleViewModel.doneNavigate()
            }
        })

        return binding.root
    }

}
