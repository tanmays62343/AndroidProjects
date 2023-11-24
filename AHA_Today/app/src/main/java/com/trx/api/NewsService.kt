package com.trx.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsService {

    //Building The Instance of the retrofit api library
    val newsInstance : NewsInterface

    //init block because this will be called whenever the instance is created
    init{
        //This is how we Initialize the retrofit library
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }

}