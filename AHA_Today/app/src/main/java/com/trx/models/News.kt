package com.trx.models

import com.google.gson.annotations.SerializedName

//This is our data class AKA model for making Storing fields to store data
data class News(
    //SerializedName property ensures that the correct field gets the correct data
    @SerializedName("totalResults")
    val totalResults : Int,

    @SerializedName("articles")
    val articles : List<Article>
)
