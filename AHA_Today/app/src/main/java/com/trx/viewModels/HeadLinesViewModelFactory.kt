package com.trx.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trx.repo.NewsRepository

//This is our ViewModelFactory Which I told u in the ViewModel Comments
class HeadLinesViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeadLinesViewModel(repository) as T
    }

}