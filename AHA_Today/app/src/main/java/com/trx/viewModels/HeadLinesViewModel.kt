package com.trx.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trx.models.News
import com.trx.repo.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeadLinesViewModel(private val repository: NewsRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews("in")
        }
    }

    val newsHeadlines: LiveData<News>
        get() = repository.newsHeadlines

}