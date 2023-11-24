package com.trx.models

import com.google.gson.annotations.SerializedName

//This is our data class AKA model for making Storing fields to store data
data class Article(
    //SerializedName property ensures that the correct field gets the correct data
    @SerializedName("author")
    val author : String,

    @SerializedName("title")
    val title : String,

    @SerializedName("description")
    val description : String,

    @SerializedName("url")
    val url : String,

    @SerializedName("urlToImage")
    val urlToImage : String,
)
