package com.example.myapplication.network

sealed class NetworkStateResource<T>(val data:T?=null, val message:String?=null) {

    class Success<T>(data: T) : NetworkStateResource<T>(data)
    class Error<T>(message: String,data: T?=null) : NetworkStateResource<T>(data,message)
    class Loading<T> : NetworkStateResource<T>()
}