package com.trx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trx.R
import com.trx.databinding.FragmentAllNewsBinding

class AllNewsFragment : Fragment() {

    private lateinit var binding : FragmentAllNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllNewsBinding.inflate(layoutInflater)

        return binding.root
    }

}