package com.example.regexquest.title

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.regexquest.R
import com.example.regexquest.database.QuizDatabase
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
                    TitleFragmentDirections.actionTitleFragmentToQuizFragment(0)
                )
                titleViewModel.doneNavigate()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.title_menu, menu)
    }
}
