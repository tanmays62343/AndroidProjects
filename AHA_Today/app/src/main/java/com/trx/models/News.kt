package com.trx.models

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("totalResults") val totalResults : Int,
    @SerializedName("articles") val articles : List<Article>
)
