package com.example.myapplication.models

data class TopHeadlines (
    val status : String,
    val totalResults : Int,
    val articles : List<News>
)