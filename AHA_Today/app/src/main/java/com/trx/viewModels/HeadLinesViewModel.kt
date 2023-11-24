package com.trx.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trx.models.News
import com.trx.repo.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//This is our ViewModel Which will Provide the Data to our Fragment/Activity
/*
Here we are passing the argument/Property in constructor so we need to create ViewModelFactory
See it is like This
NO Parameter NO Context = ViewModel()
NO Parameter BUT Context = AndroidViewModel()
Both Parameter AND Context = Create a ViewModelFactory
*/
class HeadLinesViewModel(private val repository: NewsRepository) : ViewModel() {

    //This will execute Whenever we will create Instance of the ViewModel
    init {
        //We are getting the result from API using Coroutines
        viewModelScope.launch(Dispatchers.IO) {
            //This Method we created in repository to Fetch data from API
            repository.getNewsHeadlines("in")
        }
    }

    //LiveData Because whenever it will change it will notify the concerned ones
    val newsHeadlines: LiveData<News>
        get() = repository.newsHeadlines

}