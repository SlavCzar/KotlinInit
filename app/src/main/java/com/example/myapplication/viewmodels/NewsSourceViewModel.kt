package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.models.SourceX
import com.example.myapplication.repositories.NewsSourceRepository

class NewsSourceViewModel(application: Application) : AndroidViewModel(application) {
    
    private val allSourcesList : LiveData<List<SourceX>>
    private lateinit var subscribedSourcesList : LiveData<List<SourceX>>
    private val sourceRepository = NewsSourceRepository(application)
    init {
        this.allSourcesList = sourceRepository.sourceList
        this.subscribedSourcesList = sourceRepository.subscribedSourceList
    }

//    fun getAllSources(){
//        sourceRepository.getAllSourcesFromApi()
//    }
//    fun getSubscribedSourcesFromDb(){
//        sourceRepository.getSubscribedSourcesFromDb()
//    }
//    fun insertSource(sourceX: SourceX){
//        sourceRepository.insertSource(sourceX)
//    }

}