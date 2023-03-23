package com.cuixbo.mvvm.net.core

typealias OnSuccessCallback<T> = (data: T) -> Unit
typealias OnFailureCallback = (e: RequestException) -> Unit
typealias OnUnitCallback = () -> Unit

class HttpRequestCallback<T> {

    var startCallback: OnUnitCallback? = null
    var successCallback: OnSuccessCallback<T>? = null
    var emptyCallback: OnUnitCallback? = null
    var failureCallback: OnFailureCallback? = null
    var exceptionCallback: OnFailureCallback? = null
    var finishCallback: OnUnitCallback? = null

    fun onStart(block: OnUnitCallback) {
        startCallback = block
    }

    fun onSuccess(block: OnSuccessCallback<T>) {
        successCallback = block
    }

    fun onEmpty(block: OnUnitCallback) {
        emptyCallback = block
    }

    fun onFailure(block: OnFailureCallback) {
        failureCallback = block
    }

    fun onException(block: OnFailureCallback) {
        exceptionCallback = block
    }

    fun onFinish(block: OnUnitCallback) {
        finishCallback = block
    }
}