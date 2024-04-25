package com.example.mob_dev_portfolio.util


//helps loading response
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    //only on e to inherit
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}