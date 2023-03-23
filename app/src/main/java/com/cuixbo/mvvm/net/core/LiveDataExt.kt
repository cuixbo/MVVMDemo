package com.cuixbo.mvvm.net.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


fun <T> LiveData<ApiResponse<T>>.observeState(
    owner: LifecycleOwner,
    onStart: () -> Unit,
    onSuccess: (data: T) -> Unit,
    onEmpty: () -> Unit,
    onFailure: (e: RequestException) -> Unit,
    onException: (e: RequestException) -> Unit,
    onFinish: () -> Unit,
) {
    observe(owner, object : IStateObserver<T> {

        override fun onStart() {
            onStart()
        }

        override fun onSuccess(data: T) {
            onSuccess(data)
        }

        override fun onEmpty() {
            onEmpty()
        }

        override fun onFailure(e: RequestException) {
            onFailure(e)
        }

        override fun onException(e: RequestException) {
            onException(e)
        }

        override fun onFinish() {
            onFinish()
        }

    })
}

fun <T> LiveData<ApiResponse<T>>.observeState(
    owner: LifecycleOwner,
    callback: HttpRequestCallback<T>.() -> Unit
) {
    val cb = HttpRequestCallback<T>().apply(callback)
    observe(owner, object : IStateObserver<T> {

        override fun onStart() {
            cb.startCallback?.invoke()
        }

        override fun onSuccess(data: T) {
            cb.successCallback?.invoke(data)
        }

        override fun onEmpty() {
            cb.emptyCallback?.invoke()
        }

        override fun onFailure(e: RequestException) {
            cb.failureCallback?.invoke(e)
        }

        override fun onException(e: RequestException) {
            cb.exceptionCallback?.invoke(e)
        }

        override fun onFinish() {
            cb.finishCallback?.invoke()
        }

    })

}

suspend fun <T> MutableLiveData<ApiResponse<T>>.fire(block: suspend () -> ApiResponse<T>?) {
    this.value = StartResponse()
    this.value = block()
    this.value = CompleteResponse()
}

//
//typealias MyCallbacks<T> = (
//    onStart: () -> Unit,
//    onSuccess: (data: T) -> Unit,
//    onEmpty: () -> Unit,
//    onFailure: (e: RequestException) -> Unit,
//    onException: (e: RequestException) -> Unit,
//    onFinish: () -> Unit,
//) -> Unit
//
//
//



