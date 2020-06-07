package com.example.myapplication.models


import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    val status: String,
    val sources: List<SourceX>

)