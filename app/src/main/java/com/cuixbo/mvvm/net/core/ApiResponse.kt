package com.cuixbo.mvvm.net.core

open class ApiResponse<T>(
    open val data: T? = null,
    open val errorCode: Int? = null,
    open val errorMsg: String? = null,
    open val exception: RequestException? = null,
) {
    val success: Boolean
        get() {
            return errorCode == 0
        }
}

class StartResponse<T> : ApiResponse<T>()

data class SuccessResponse<T>(override val data: T) : ApiResponse<T>(data)

class EmptyResponse<T> : ApiResponse<T>()

data class FailureResponse<T>(override val exception: RequestException) : ApiResponse<T>(exception = exception)

data class ExceptionResponse<T>(override val exception: RequestException) :
    ApiResponse<T>(exception = exception)

class CompleteResponse<T> : ApiResponse<T>()