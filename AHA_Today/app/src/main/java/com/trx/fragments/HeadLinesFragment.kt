package com.trx.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.trx.R
import com.trx.adapters.NewsAdapter
import com.trx.api.NewsService
import com.trx.databinding.FragmentHeadLinesBinding
import com.trx.repo.NewsRepository
import com.trx.viewModels.HeadLinesViewModel
import com.trx.viewModels.HeadLinesViewModelFactory

class HeadLinesFragment : Fragment() {

    private lateinit var binding : FragmentHeadLinesBinding

    //Initializing ViewModel
    private lateinit var headLinesViewModel : HeadLinesViewModel

    //Initializing Adapter for our Recycler view
    private lateinit var adapter: NewsAdapter

    //We are in Fragment so this will execute Before the View is created (Refer CheezyCode Vid)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeadLinesBinding.inflate(layoutInflater)
        return binding.root
    }

    //This will be executed after the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //creating Every instances which we will need
        val newsService = NewsService.newsInstance
        val repository = NewsRepository(newsService)

        //Start looking what we need from here
        headLinesViewModel = ViewModelProvider(this,
            HeadLinesViewModelFactory(repository))[HeadLinesViewModel::class.java]

        //Getting the Data From our ViewModel Which it is Bringing form Repository
        headLinesViewModel.newsHeadlines.observe(requireActivity(), Observer {
            Log.d("BRB",it.toString())
            adapter = NewsAdapter(requireContext(),it.articles)
            binding.headlineRecycler.adapter = adapter
        })

    }

}