package com.cuixbo.mvvm.net.core

import com.google.gson.JsonParseException
import kotlinx.coroutines.delay
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException

open class BaseRepo {
    suspend fun <T> fetch(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        delay(5000)
        runCatching {
            println("fetch-blocking")
            block()
        }.onSuccess {
//            println("fetch-onSuccess")
            // 网络、解析、转换等都正常返回
            return handleHttpSuccess(it)
        }.onFailure {
//            println("fetch-onFailure")
            // 上述有失败的情况
            return handleHttpFailure(it)
        }
        return EmptyResponse()
    }

    private fun <T> handleHttpSuccess(res: ApiResponse<T>): ApiResponse<T> {
        // 判断 errcode码 ，区分是业务正常还是异常
        return if (res.success) {
            if (res.data == null || (res.data is List<*> && (res.data as List<*>).isEmpty()))
                EmptyResponse()
            else
                SuccessResponse(res.data!!)
        } else {
            FailureResponse(RequestException(res))
        }
    }

    private fun <T> handleHttpFailure(e: Throwable): ApiResponse<T> {
        return FailureResponse(handleException(e))
    }

    private fun handleException(e: Throwable): RequestException = when (e) {
        is HttpException, is IOException -> RequestException(1001, "网络异常", e.message)
        is JsonParseException, is JSONException -> RequestException(1011, "Json解析失败", e.message)
        else -> RequestException(101, "未知异常", e.message)
    }
}