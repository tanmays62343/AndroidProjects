package com.trx.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trx.api.NewsInterface
import com.trx.models.News


class NewsRepository(private val apiService: NewsInterface) {

    //This is Best Practice to access the data which is coming from API

    //This is MutableLiveData Data that can be changed
    private val _newsHeadlines = MutableLiveData<News>()

    //This is LiveData Data that cannot be changed hence made available to public
    val newsHeadlines: LiveData<News>
        get() = _newsHeadlines

    //Function to get data from API
    suspend fun getNewsHeadlines(country: String) {

        //Calling the Method of our interface
        val result = apiService.getHeadlines(country)

        //Handling the Null Result
        if(result.body() != null ) {
            _newsHeadlines.postValue(result.body())
        }else {
            Log.d("BRB","The API has returned Empty Body")
        }

    }

}