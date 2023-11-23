package com.trx.api

import com.trx.models.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "c49ef6c8c3a1451b8d827cd617328fd1"

interface NewsInterface {

    @GET("top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country : String, @Query("page") page : Int) : Call<News>

    //https://newsapi.org/v2/top-headlines?apiKey=c49ef6c8c3a1451b8d827cd617328fd1&country=in&page=1

}
