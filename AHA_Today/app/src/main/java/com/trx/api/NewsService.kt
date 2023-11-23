package com.trx.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsService {
    val newsInstance : NewsInterface

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }

}