package com.cuixbo.mvvm.net.core

import androidx.lifecycle.Observer

interface IStateObserver<T> : Observer<ApiResponse<T>> {
    override fun onChanged(t: ApiResponse<T>?) {
        when (t) {
            is StartResponse -> {onStart()}
            is SuccessResponse -> {onSuccess(t.data)}
            is EmptyResponse -> {onEmpty()}
            is FailureResponse -> {onFailure(t.exception)}
            is ExceptionResponse -> {onException(t.exception)}
            is CompleteResponse -> {onFinish()}
        }
    }

    /**
     * 请求开始
     */
    fun onStart()

    /**
     * 请求成功，且 data 不为 null
     */
    fun onSuccess(data: T)

    /**
     * 请求成功，但 data 为 null 或者 data 是集合类型但为空
     */
    fun onEmpty()

    /**
     * 请求失败
     */
    fun onFailure(e: RequestException)

    /**
     * 请求异常
     */
    fun onException(e: RequestException)

    /**
     * 请求结束
     */
    fun onFinish()
}