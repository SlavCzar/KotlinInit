package com.example.myapplication.models


import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    val sources: List<SourceX>,
    val status: String
)