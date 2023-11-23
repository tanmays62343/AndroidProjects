package com.trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.trx.R
import com.trx.api.NewsService
import com.trx.models.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNews()
    }

    //Fetching News from API
    private fun getNews() {
        val getNews = NewsService.newsInstance.getHeadlines("in",1)
        getNews.enqueue(object : Callback<News>{

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if(news != null){
                    Log.d("BRB",news.toString())
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("BRB","Error in Fetching News")
            }
        })
    }

}