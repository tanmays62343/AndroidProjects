package com.trx.api

import com.trx.models.News
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//These are constants, can be created in another file
const val BASE_URL = "https://newsapi.org/v2/"      //This will remain same for every end point
const val API_KEY = "c49ef6c8c3a1451b8d827cd617328fd1"        //Generated API Key

//This is the interface for our API Here we Declare the methods which we will be using
interface NewsInterface {

    //This Are END_Points which we want to HIT and get data (There is another method - HashMap_Method)
    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun getHeadlines(@Query("country") country : String) : Response<News>
    //Note - Here we are returning Response which is used with Coroutines
    //if we don't want that we can return Call<News>

    //This endpoint is not being used as of now
    @GET("everything?apiKey=$API_KEY")
    suspend fun getAllNews(@Query("q") topic : String) : Response<News>

    //The final URL will look like this-
    //https://newsapi.org/v2/top-headlines?apiKey=c49ef6c8c3a1451b8d827cd617328fd1&country=in

}
