package com.example.myapplication.network


/**
 * A class that takes a generic type to maintain the loading, success and error states
 * A LiveData object of this class is used to observe the network states and update the UI
 */

sealed class NetworkStateResource<T>(val data:T?=null, val message:String?=null) {

    class Success<T>(data: T) : NetworkStateResource<T>(data)
    class Error<T>(message: String,data: T?=null) : NetworkStateResource<T>(data,message)
    class Loading<T> : NetworkStateResource<T>()
}