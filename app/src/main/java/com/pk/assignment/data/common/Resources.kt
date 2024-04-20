package com.pk.assignment.data.common

sealed class Resources<T>(val message: String? = null, val data: T? = null) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(message: String?) : Resources<T>(message = message)
}