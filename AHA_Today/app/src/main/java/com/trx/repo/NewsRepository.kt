package com.trx.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trx.api.NewsInterface
import com.trx.models.News


class NewsRepository(private val apiService: NewsInterface) {


    private val _newsHeadlines = MutableLiveData<News>()

    val newsHeadlines: LiveData<News>
        get() = _newsHeadlines

    suspend fun getNews(country: String) {
        val result = apiService.getHeadlines(country)

        if(result.body() != null ) {
            _newsHeadlines.postValue(result.body())
        }

    }

}