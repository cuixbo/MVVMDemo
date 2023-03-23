package com.cuixbo.mvvm.net.core

data class RequestException(
    val code: Int? = 0,
    val errorMsg: String? = "",
    val error: String? = null
) : Exception() {

    constructor(response: ApiResponse<*>) : this(
        response.errorCode,
        response.errorMsg,
        response.errorMsg
    )

    constructor(httpError: HttpError, error: String?) : this(
        httpError.code,
        httpError.message,
        error
    )

}